<?php
include 'connection.php';
try {
  //接結の設定
  $db = new PDO(PDO_DNS, DB_USERNAME, DB_PASSWORD);
  $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

  $sql = $db->prepare("select * from tbllogin");
  $sql->execute();
  $rows = $sql->fetchAll(PDO::FETCH_ASSOC);

  //ファイル処理
  $handle = fopen(exec('echo %SystemDrive%') . '\\Users\\' . get_current_user() . '\\OneDrive\\デスクトップ\\member.csv', "w");
  foreach ($rows as $row) {
    fputcsv($handle, $row);
  }
  fclose($handle);

  echo json_encode("成功！");
}
//エラー発生
catch (PDOException $e) {
  $db->rollBack();
  echo json_encode("失敗！");
}
