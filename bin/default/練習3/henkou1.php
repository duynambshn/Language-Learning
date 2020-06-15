<?php
include 'connection.php';
//変更画面２から入力データでデータベースに変更する
//入力データがある場合
if (@$_POST['txtID'] && @$_POST['txtPW'] && @$_POST['oldID']) {
  try {
    //接結の設定
    $db = new PDO(PDO_DNS, DB_USERNAME, DB_PASSWORD);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $sql = $db->prepare("update tbllogin set ID = :ID, PW = :PW where ID = :oldID");
    //IDセット
    $sql->bindParam(':oldID', $_POST['oldID'], PDO::PARAM_STR);
    $sql->bindParam(':ID', $_POST['txtID'], PDO::PARAM_STR);
    //PWセット
    $sql->bindParam(':PW', $_POST['txtPW'], PDO::PARAM_STR);

    $sql->execute();
  }
  //エラー発生
  catch (PDOException $e) {
    //nothing
  }
}

?>

<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="utf-8">
  <title>変更画面</title>
</head>

<body style="text-align: center; background-color: whitesmoke">
  <h1 style="color: blueviolet;">変更画面</h1>

  <form action="henkou2.php" method="POST">
    <div>
      <label for="txtID" style="margin-right: 55px;">ID</label>
      <input type="text" name="txtID" value="" required>
    </div>

    <div>
      <label for="txtPW" style="width: 50px">Password</label>
      <input type="password" name="txtPW" value="" required>
    </div>

    <div style="margin-left: 150px;">
      <input type="submit" value="検索">
      <input type="reset" value="クリア">
    </div>
  </form>

</body>

</html>