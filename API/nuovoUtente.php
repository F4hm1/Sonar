<?php
    $connessione=mysqli_connect("localhost","gas","tourette","sonar");
    if (mysqli_connect_errno()) {
        echo "Errore nella connessione al server <br />".mysqli_connect_error();
        exit();
    }else{
        echo "Connessione eseguita correttamente <br />";
    }
    $json=$_GET['json'];
    $json=urldecode($json);
    $json=json_decode($jsone,true);
    $username=$json['username'];
    $password=$json['password'];
    $name=$json['name'];
    $surname=$json['surname'];
    $dob=$json['dob'];
    $email=$json['email'];
    $nuovoutente="INSERT INTO users (username,password,name,surname,dob,email) VALUES ('$username','$password','$name','$surname','$dob','$email');";
    $result=$connessione->query($nuovoutente);
    $tmp_img = $this->image['tmp_name'];
    $sql = "INSERT INTO ImageStore(ImageId,Image) VALUES('$this->image_id','" . mysql_escape_string(file_get_contents($tmp_image)) . "')";
    mysql_query($sql);
    echo $nuovoutente;
    if($result){
        echo "Utente creato <br />";
    }
    $connessione->close();
 ?>
