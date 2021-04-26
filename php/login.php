<?php
session_start();
?>
<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<head>
<style>
body {font: 14px sans-serif;}
form {
  margin-top: 50px;
  margin-bottom: 20px;
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
</style>
</head>
<body>
    
<form method="post" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
  
  <p><div class="container">
  <select name="Key">
    <option value="Generate">Generate</option>
    <option value="Reset">Reset</option>
    <option value="Delete">Delete</option>
  </select>
  </div></p>
  
  <div class="container">
    <label for="uname"><b>Number Of Keys</b></label>
    <input type="text" placeholder="Enter Value" name="uname" required>

    <label for="psw"><b>Number Of Days</b></label>
    <input type="text" placeholder="Enter Value" name="psw" required>
    <center><button name="submit" class="btn btn-primary" type="submit">SUBMIT</button></center>
  </div>
  
</form>

<p><div class="container">
    
<?php

if($_SESSION["username"] == "SameerArora" && $_SESSION["password"] == "sameerbb"){
    
if ($_SERVER["REQUEST_METHOD"] == "POST"){
    
    if(isset($_POST["submit"])){
        
        $_SESSION["ndays"] = test_input($_POST["psw"]);
        $_SESSION["nkey"] = test_input($_POST["uname"]);
        
        if(!$_SESSION["ndays"] == ''){
        header("Location: https://pubgwale.com/test/display.php");
        }
        
    }
}
    
} else {
  header("Location: http://pubgwale.com/test/");
  die();
}

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}

?>
    
</div></p>

</body>
</html>