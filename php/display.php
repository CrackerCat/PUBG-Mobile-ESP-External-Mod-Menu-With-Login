<?php
session_start();
?>
<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
<style>
body{
    padding:16px;
}
table {
  width:100%;
}
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}
th, td {
  padding: 15px;
  text-align: left;
}
#t01 tr:nth-child(even) {
  background-color: #eee;
}
#t01 tr:nth-child(odd) {
 background-color: #fff;
}
#t01 th {
  background-color: black;
  color: white;
}
</style>
</head>
<body>

<?php

function generateRandomString($length) {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}

if($_SESSION["username"] == "SameerArora" && $_SESSION["password"] == "sameerbb"){
    
  $ndays = $_SESSION["ndays"];
  $nkey = $_SESSION["nkey"];
  $date = date('d-m-Y');
  $date = date("d-m-Y",strtotime("$date +$ndays day"));
  
  echo '<table id="t01"><tr><th>id</th><th>Date</th><th>Key</th></tr>';
    
    
if (!$_SESSION["ndays"] == ""){
  
  for ($i = 0; $i < $nkey; $i++){
          $id = $i+1;
          $key = "s".generateRandomString(11).":".generateRandomString(8);
          $fileContent = file_get_contents('json_date.txt');
          $decoded = json_decode($fileContent,true);
          $array = array_merge($decoded,array($key=>$date));
          $serializedData = json_encode($array);
          file_put_contents('json_date.txt', $serializedData);
          $table = '<tr><td>'.$id.'</td><td>'.$date.'</td><td>'.$key.'</td></tr>';
          echo $table;
  }

}

echo '</table>';

} else {
  header("Location: http://pubgwale.com/test/");
  die();
}

?>

</body>
</html>
