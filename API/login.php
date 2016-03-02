<?php
    $connessione=mysqli_connect("localhost","gas","tourette","sonar");
    $user=$_GET['username'];
    $password=$_GET['password'];
    $login="SELECT * FROM users WHERE username='".$user."' AND password='".$password."';";
    $result=$connessione->query($login);
    if($result->num_rows>0){
        echo "Utente loggato";
    }else{
        echo "Login errato";
    }
    echo $login;
    $connessione->close();
 ?>
