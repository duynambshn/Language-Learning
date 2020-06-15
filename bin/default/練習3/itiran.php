<?php
include 'connection.php';
//登録画面１から入力データでデータベースに登録する
//入力データがある場合
try {
  //接結の設定
  $db = new PDO(PDO_DNS, DB_USERNAME, DB_PASSWORD);
  $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

  $sql = $db->prepare("select * from tbllogin");

  $sql->execute();

  //取得成功
  if ($sql->rowCount() > 0) {
    $result = $sql->fetchAll();
    $rowCount = $sql->rowCount();
  }
}
//エラー発生
catch (PDOException $e) {
  $result = false;
}

?>

<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="utf-8">
  <title>一覧画面</title>
  <style>
    table {
      border-collapse: collapse;
    }

    table,
    th,
    td {
      border: 1px solid black;
    }

    th,
    td {
      width: 200px;
    }
  </style>
</head>

<body style="text-align: center; background-color: whitesmoke">
  <h1 style="color: blueviolet;">一覧画面 <a href="">close</a></h1>

  <table style="margin: 0 auto; ">
    <tr>
      <th>ID</th>
      <th>Pass</th>
    </tr>

    <!-- PHP処理開始 -->
    <!-- データを表れる -->
    <?php
    if (isset($result))
      for ($i = 0; $i < $rowCount; $i++) {
        echo "<tr>";
        echo "<td> " . $result[$i]['ID'] . "</td>";
        echo "<td> " . $result[$i]['PW'] . "</td>";
        echo "</tr>";
      }
    ?>
    <!-- PHP処理終了 -->
  </table>

  <form action="" method="POST" name="myForm" onsubmit="onClick_PageChange()">

    <input type="radio" name="page" value="CSV.php" checked>
    <label for="CSV">CSV</label><br>
    <input type="radio" name="page" value="henkou1.php">
    <label for="henkou">変更</label><br>

    <div style="margin-left: 150px; margin-top: 20px;">
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