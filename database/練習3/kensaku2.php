<?php
include 'connection.php';
//検索画面１から入力データでデータベースに検索する
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
  <title>検索結果画面</title>
</head>

<body style="text-align: center; background-color: whitesmoke">
  <h1 style="color: blueviolet;">検索結果画面</h1>

  <form action="" method="POST" name="myForm" onsubmit="onClick_PageChange()">

    <!-- PHP処理 -->
    <?php
    //検索失敗の場合
    echo "<p style='color:red'>";
    if (!$result) {
      echo "エラー発生";
    }
    //成功の場合
    else {
      echo "検索成功";
      echo "<br>　　ID:" . $result['ID'];
      echo "<br>　　PW:" . $result['PW'];
    }
    echo "<p>";
    ?>

    <input type="radio" name="page" value="kensaku1.php" checked>
    <label for="kensaku">検索へ</label><br>
    <input type="radio" name="page" value="henkou1.php">
    <label for="henkou">変更へ</label><br>

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