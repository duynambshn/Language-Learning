<?php
include 'connection.php';
//登録画面１から入力データでデータベースに登録する
//入力データがある場合
if (@$_POST['txtID'] && @$_POST['txtPW']) {
  try {
    $result = "";

    //接結の設定
    $db = new PDO(PDO_DNS, DB_USERNAME, DB_PASSWORD);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $sql = $db->prepare("insert into tbllogin(ID, PW) values (:ID, :PW)");
    //IDセット
    $sql->bindParam(':ID', $_POST['txtID'], PDO::PARAM_STR);
    //PWセット
    $sql->bindParam(':PW', $_POST['txtPW'], PDO::PARAM_STR);

    $sql->execute();

    //登録成功
    if ($sql->rowCount() > 0)
      $result = "登録成功";

    //登録失敗
    else
      $result = "エラー発生";
  }
  //エラー発生
  catch (PDOException $e) {

    $result = "エラー発生";
  }
}

?>


<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="utf-8">
  <title>登録結果画面</title>
</head>

<body style="text-align: center; background-color: whitesmoke">
  <h1 style="color: blueviolet;">登録結果画面</h1>

  <form action="" method="POST" name="myForm" onsubmit="onClick_PageChange()">
    <p style="color:red"><?= $result ?></p>
    <input type="radio" name="page" value="touroku1.php" checked>
    <label for="touroku">登録へ</label><br>
    <input type="radio" name="page" value="kensaku1.php">
    <label for="kensaku">検索へ</label><br>

    <div style="margin-left: 150px;">
      <input type="submit" value="実行">
    </div>
  </form>

  <script>
    function onClick_PageChange() {
      document.forms[0].action = document.querySelector("input[name='page']:checked").value;
    }
  </script>
</body>

</html>