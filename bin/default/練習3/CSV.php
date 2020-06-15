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
  <h1 style="color: blueviolet;">一覧画面<a href="http://" style="margin-left: 50px; border-style: solid">X</a></h1>

  <table id='tbl' style="margin: 0 auto; ">
    <thead>
      <tr>
        <th>No</th>
        <th>Origin</th>
        <th>Translate</th>
        <th>Description</th>
      </tr>
    </thead>
    <tbody>

    </tbody>
  </table>

  <form action="touroku1.php" method="POST" name="myForm" onsubmit="return onClick_PageChange()">

    <!-- ラジオボタン -->
    <input type="radio" name="page" value="CSV.php" id="nyuuryoku" checked onchange="onClick_RadioChange('入力')" style="margin-top: 30px">
    <label for="nyuuryoku">入力<br>CSVファイル-->データベース</label><br>
    <input type="radio" name="page" value="CSV.php" id="syuturyoku" onchange="onClick_RadioChange('出力')" style="margin-top: 10px">
    <label for="syuturyoku">出力<br>データベース-->CSVファイル</label><br>
    <input type="radio" name="page" value="touroku1.php" onchange="onClick_RadioChange('')" style="margin-top: 10px">
    <label for="touroku">登録</label><br>

    <div style="margin-left: 150px; margin-top: 20px;">
      <input type="submit" value="実行">
      <input type="button" value="クリア" onclick="ClearRow()">
    </div>
  </form>

  <script>
    function onClick_PageChange() {
      var selVal = document.querySelector("input[name='page']:checked");

      //登録ラジオボタンを選択する場合
      if (selVal.value !== "CSV.php") {
        document.forms[0].action = document.querySelector("input[name='page']:checked").value;
        return true;
      }
      //以外場合
      else {

        var resultVal;
        var row = document.getElementsByTagName('tbody')[0];
        var tblShow = document.getElementById('tbl');
        var xmlhttp = new XMLHttpRequest();
        var xmlhttpSQL = new XMLHttpRequest();

        //Clear table data
        row.parentNode.removeChild(row);

        //入力の場合
        if (selVal.id === "nyuuryoku") {

          //CSVのデータを表示する        
          xmlhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {

              resultVal = this.responseText;
              var myObj = JSON.parse(this.responseText);

              row = document.createElement("TBODY");

              myObj.forEach(val => {
                var tRow = document.createElement("TR");
                var colID = document.createElement("TD");
                var colPW = document.createElement("TD");
                var colDes = document.createElement("TD");
                var colNo = document.createElement("TD");

                colNo.innerHTML = val['no'];
                colID.innerHTML = val['origin'];
                colPW.innerHTML = val['trans'];
                colDes.innerHTML = val['des'];

                //Create row
                tRow.appendChild(colNo);
                tRow.appendChild(colID);
                tRow.appendChild(colPW);
                tRow.appendChild(colDes);

                //Add row into body
                row.appendChild(tRow);
              });

              //Add body into table
              tblShow.appendChild(row)


              //データベースにCSVのデータを登録す
              xmlhttpSQL.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                  alert(JSON.parse(this.responseText).toString());
                }
              };
              xmlhttpSQL.open("POST", "CSVtouroku.php", true);
              xmlhttpSQL.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
              xmlhttpSQL.send("data=" + resultVal);
              //-------------------------------------------------
            }
          };
          xmlhttp.open("GET", "CSVread.php", true);
          xmlhttp.send();
          //-------------------------------------------------
        }

        //出力の場合
        if (selVal.id === "syuturyoku") {
          //データベースのデータを表示する        
          xmlhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {

              resultVal = this.responseText;
              var myObj = JSON.parse(this.responseText);

              row = document.createElement("TBODY");

              myObj.forEach(val => {
                var tRow = document.createElement("TR");
                var colID = document.createElement("TD");
                var colPW = document.createElement("TD");

                colID.innerHTML = val['ID'];
                colPW.innerHTML = val['PW'];

                //Create row
                tRow.appendChild(colID);
                tRow.appendChild(colPW);

                //Add row into body
                row.appendChild(tRow);
              });

              //Add body into table
              tblShow.appendChild(row)

              xmlhttpSQL.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                  alert(JSON.parse(this.responseText).toString());
                }
              }
              xmlhttpSQL.open("GET", "CSVwrite.php", true);
              xmlhttpSQL.send();
            }
          }

          xmlhttp.open("GET", "CSVload.php", true);
          xmlhttp.send();
        }

        //Submit Cancel
        return false;
      }
    }

    function onClick_RadioChange(val) {
      document.getElementsByName("CSV-syori").value = val;
    }

    function ClearRow() {
      var row = document.getElementsByTagName('tbody')[0];
      //Clear table data
      row.parentNode.removeChild(row);

      //Create empty Tbody
      document.getElementById('tbl').appendChild(document.createElement("TBODY"));
    }
  </script>
</body>

</html>