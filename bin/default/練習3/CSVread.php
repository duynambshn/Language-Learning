<?php
$jsonData = array();
// $handle = fopen(exec('echo %SystemDrive%') . '\\Users\\' . get_current_user() . '\\OneDrive\\デスクトップ\\member.csv', "r");
$handle = fopen('C:\\LAY DUY NAMフォルダー\\EnglishJapan\\Eng_Jpn.csv', "r");

$flag = true;

if ($handle !== FALSE) {
  while (($data = fgetcsv($handle)) !== FALSE) {
    if ($flag) {
      $flag = !$flag;
      continue;
    }

    array_push($jsonData, array('no' => $data[0], 'origin' => $data[1], 'trans' => $data[2], 'des' => $data[3]));
  }
}
fclose($handle);

echo json_encode($jsonData);
