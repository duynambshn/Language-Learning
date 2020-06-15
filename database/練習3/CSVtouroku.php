<?php
header("Content-Type: application/json; charset=UTF-8");
$jsonData = json_decode($_POST["data"], false);

if (count($jsonData) > 0) {
  include 'connection.php';
  try {
    //接結の設定
    $db = new PDO(PDO_DNS, DB_USERNAME, DB_PASSWORD);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $dic = 1;

    $db->beginTransaction();
    // $sql = $db->prepare("insert into tbllogin(ID, PW) values (:ID, :PW)");
    $sql = $db->prepare("insert into sentence(id, origin_sentence, translate_sentence, explanation, dictionary_id) 
                        values (:id, :origin_sentence, :translate_sentence, :explanation, :dictionary_id)");

    for ($i = 0; $i < count($jsonData); $i++) {
      //IDセット
      $sql->bindParam(':id', $jsonData[$i]->no, PDO::PARAM_STR);
      //PWセット
      $sql->bindParam(':origin_sentence', $jsonData[$i]->origin, PDO::PARAM_STR);
      $sql->bindParam(':translate_sentence', $jsonData[$i]->trans, PDO::PARAM_STR);
      $sql->bindParam(':explanation', $jsonData[$i]->des, PDO::PARAM_STR);
      $sql->bindParam(':dictionary_id', $dic, PDO::PARAM_STR);
      $sql->execute();
    }
    $db->commit();

    echo json_encode("成功！");
  }
  //エラー発生
  catch (PDOException $e) {
    $db->rollBack();
    echo json_encode("失敗！");
  }
} else {
  echo json_encode("失敗！");
}
