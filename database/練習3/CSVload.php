<?php
include 'connection.php';
try {
  //接結の設定
  $db = new PDO(PDO_DNS, DB_USERNAME, DB_PASSWORD);
  $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

  $sql = $db->prepare("select * from tbllogin");
  $sql->execute();
  echo json_encode($sql->fetchAll(PDO::FETCH_ASSOC));
}
//エラー発生
catch (PDOException $e) {
}
