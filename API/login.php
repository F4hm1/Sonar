<?php
    $connessione=mysqli_connect("localhost","gas","tourette","sonar");
    if (mysqli_connect_errno()) {
        //echo "Errore nella connessione al server \n".mysqli_connect_error();
        exit();
    }else{
        //echo "Clonnessione eseguita correttamente \n";
    }
    $json=$_REQUEST['login'];
    echo $json;
    //print_r($json."\n");
    $json=urldecode($json);
    //print_r($json."\n");
    $json=json_decode($json,true);

    //print_r($json."\n");
    $user=$json['username'];
    $password=$json['password'];
    //echo "json= ".$json."; user= ".$user."; password= ".$password;
    $login="\nSELECT * FROM users WHERE username='".$user."' AND password='".$password."';";
    $result=$connessione->query($login);
    if($result->num_rows>0){
        //echo "\nUtente loggato";
	      echo "{\"result\":\"ok\",\"username\":\"".$user."\",\"password\":\"".$password."\"}";
    }else{
        //echo "\nLogin errato";
	      echo "{\"result\":\"ko\",\"username\":\"".$user."\",\"password\":\"".$password."\"}";
    }
    //echo $login;
    $connessione->close();
 ?>
