<?php

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

?>