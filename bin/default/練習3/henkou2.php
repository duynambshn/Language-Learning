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

    $sql = $db->prepare("select * from tbllogin where ID = :ID and PW = :PW");
    //IDセット
    $sql->bindParam(':ID', $_POST['txtID'], PDO::PARAM_STR);
    //PWセット
    $sql->bindParam(':PW', $_POST['txtPW'], PDO::PARAM_STR);

    $sql->execute();

    //登録成功
    if ($sql->rowCount() > 0) {
      $result = $sql->fetch(PDO::FETCH_ASSOC);
    }
    //登録失敗
    else
      $result = false;
  }
  //エラー発生
  catch (PDOException $e) {
    $result = false;
  }
}

?>

<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="utf-8">
  <title>変更結果画面</title>
</head>

<body style="text-align: center; background-color: whitesmoke">
  <h1 style="color: blueviolet;">変更結果画面</h1>

  <form action="" method="POST" name="myForm" onsubmit="onClick_PageChange()">

    <!-- PHP処理開始 -->
    <!-- 検索失敗の場合 -->
    <?php if (!$result) : ?>
      <p style='color:red'>エラー発生<p>

          <!-- 成功の場合 -->
        <?php else : ?>
          <div style='color:red'>
            <label style="margin-right: 100px">検索成功</label>
            <label>変更テスト</label>
          </div>
          <div>
            <label for="txtID" style="margin-right: 55px;">ID</label>
            <input type="hidden" name="oldID" value="<?= @$_POST['txtID']; ?>">
            <input type="text" name="txtID" value="<?= @$_POST['txtID']; ?>" required>
          </div>

          <div>
            <label for="txtPW">Password</label>
            <input type="password" name="txtPW" value="<?= @$_POST['txtPW'] ?>" required>
          </div>
        <?php endif; ?>
        <!-- PHP処理終了 -->

        <input type="radio" name="page" value="henkou1.php" checked>
        <label for="henkou">変更</label><br>
        <input type="radio" name="page" value="itiran.php">
        <label for="itiran">一覧</label><br>

        <div style="margin-left: 150px;">
          <input type="submit" value="実行">
          <input type="reset" value="クリア">
        </div>
  </form>

  <script>
    function onClick_PageChange() {
      document.forms[0].action = document.querySelector("input[name='page']:checked").value;
    }
  </script>
</body>

</html>