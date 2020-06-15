<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="utf-8">
  <title>登録入力画面</title>
</head>

<body style="text-align: center; background-color: whitesmoke">
  <h1 style="color: blueviolet;">登録入力画面</h1>

  <form action="touroku2.php" method="POST">
    <div>
      <label for="txtID" style="margin-right: 55px;">ID</label>
      <input type="text" name="txtID" value="" required>
    </div>

    <div>
      <label for="txtPW" style="width: 50px">Password</label>
      <input type="password" name="txtPW" value="" required>
    </div>

    <div style="margin-left: 150px;">
      <input type="submit" value="登録">
      <input type="reset" value="クリア">
    </div>
  </form>
</body>

</html>