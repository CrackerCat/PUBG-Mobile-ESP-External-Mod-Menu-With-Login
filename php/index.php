<?php
session_start();
?>

<?php

$id = "s".$_GET['id'];
$key = $_GET['key'];

if(!$_GET['id'] == '' && !$_GET['key'] == ''){

$id = "s".$_GET['id'];
$key = $_GET['key'];

$fileContent = file_get_contents('json_date.txt');
$decoded = json_decode($fileContent,true);

if(array_key_exists($key,$decoded)){
    
$decode = json_decode($fileContent);
$date = $decode->$key;

$fileContent = file_get_contents('json_array.txt');
$decoded = json_decode($fileContent,true);

if(!array_key_exists($id,$decoded)){

$array = array_merge($decoded,array($id=>$key));

$serializedData = json_encode($array);
file_put_contents('json_array.txt', $serializedData);

$fileContent = file_get_contents('json_array.txt');
$decoded = json_decode($fileContent,true);

if (in_array($key,$decoded)){
    
  $verification = array_search($key,$decoded);
  if(!strcmp($id,$verification) == 0){
      $serializedData = json_encode(array_unique($decoded));
      file_put_contents('json_array.txt', $serializedData);
      echo "2";
  } else {
$file = file_get_contents('json_array.txt');
$decode = json_decode($file);

$User = substr($decode->$id,0,12);
$Pass = substr($decode->$id,13,21);
$verify = $User.":".$Pass;

if(strcmp($key, $verify) == 0){
   echo $date;
} else {
   echo "11"; 
}
}

}
   
} else {
  
if (in_array($key,$decoded)){
    
  $verification = array_search($key,$decoded);
  if(!strcmp($id,$verification) == 0){
      echo "2";
  } else {
$file = file_get_contents('json_array.txt');
$decode = json_decode($file);

$User = substr($decode->$id,0,12);
$Pass = substr($decode->$id,13,21);
$verify = $User.":".$Pass;

if(strcmp($key, $verify) == 0){
   echo $date;
} else {
   echo "1"; 
}
}

} else {
   
$array = array_replace($decoded,array($id=>$key));

$serializedData = json_encode($array);
file_put_contents('json_array.txt', $serializedData);

$fileContent = file_get_contents('json_date.txt');
$decode = json_decode($fileContent);
echo $decode->$key;

}

} 

} else {
    echo "1";
}

}


if($_GET['id'] == '' && $key == ''){

echo '<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<style>
body {font: 14px sans-serif;}
form {
  margin: 0;
  position: absolute;
  top: 30%;
  -ms-transform: translateY(-30%);
  transform: translateY(-30%);
}

input[type=text], input[type=password] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
}

button {
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 50%;
}

button:hover {
  opacity: 0.8;
}

.cancelbtn {
  width: auto;
  padding: 10px 18px;
  background-color: #f44336;
}

.imgcontainer {
  text-align: center;
  margin: 24px 0 12px 0;
}

img.avatar {
  width: 25%;
  border-radius: 50%;
}

.container {
  padding: 16px;
}

span.psw {
  float: right;
  padding-top: 16px;
}

/* Change styles for span and cancel button on extra small screens */
@media screen and (max-width: 300px) {
  span.psw {
     display: block;
     float: none;
  }
  .cancelbtn {
     width: 100%;
  }
}
</style>
</head>
<body>

<form method="post" action="">

<center><h2>Login Form</h2></center>

  <div class="imgcontainer">
    <img src="avatar.png" alt="Avatar" class="avatar">
  </div>
  
  <center><br><p>Please fill in your credentials to login.</p><br></center>

  <div class="container">
    <label for="uname"><b>Username</b></label>
    <input type="text" placeholder="Enter Username" name="uname" required>

    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="psw" required>
        
    <center><button class="btn btn-primary" type="submit">LOGIN</button></center>
  </div>
</form></body></html>';

$name = $pass = "";

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
  $_SESSION["username"] = test_input($_POST["uname"]);
  $_SESSION["password"] = test_input($_POST["psw"]);
}

if($_SESSION["username"] == "SameerArora" && $_SESSION["password"] == "sameerbb"){
    header("Location: https://pubgwale.com/test/login.php");
    die();
}

}

?>
