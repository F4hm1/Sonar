<?php
    $connessione=mysqli_connect("localhost","gas","tourette","sonar");
    if (mysqli_connect_errno()) {
        echo "Errore nella connessione al server <br />".mysqli_connect_error();
        exit();
    }else{
        echo "Connessione eseguita correttamente <br />";
    }
    $username=$_GET['username'];
    $password=$_GET['password'];
    $name=$_GET['name'];
    $surname=$_GET['surname'];
    $dob=$_GET['dob'];
    $email=$_GET['email'];
    $nuovoutente="INSERT INTO users (username,password,name,surname,dob,email) VALUES ('$username','$password','$name','$surname','$dob','$email');";
    $result=$connessione->query($nuovoutente);
    echo $nuovoutente;
    if($result){
        echo "Utente creato <br />";
    }
    $connessione->close();
 ?>
