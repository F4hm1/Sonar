<?php
    $connessione=mysqli_connect("sql.paws.it","pawsit63207","tettarella","pawsit63207");
    if (mysqli_connect_errno()) {
        //echo "Errore nella connessione al server \n".mysqli_connect_error();
        exit();
    }else{
        //echo "Connessione eseguita correttamente \n";
    }
    $json=$_REQUEST['login'];
    $json=urldecode($json);
    $json=json_decode($json,true);
    $user=$json['username'];
    $password=$json['password'];
    $login="\nSELECT * FROM users WHERE username='".$user."' AND password='".$password."';";
    $result=$connessione->query($login);
    if($result->num_rows>0){
          $sql = "SELECT name,surname,username,email,id
                  FROM users
                  WHERE username = '".$user."' AND password = '".$password."'"
                  ;

            if(!$data = $connessione->query($sql)){
                  die('There was an error running the query [' . $connessione->error . ']');
            }

            while($row = $data->fetch_assoc()){
                  //echo $row['username'] . '<br />';
                  echo "{\"result\":\"ok\",\"id\":\"".$row['id']."\",\"nome\":\"".$row['name']."\",\"cognome\":\"".$row['surname']."\",\"email\":\"".$row['email']."\",\"username\":\"".$row['username']."\"}";
            }

	    //echo "{\"result\":\"ok\",\"id\":\"".$id."\",\"nome\":\"".$nome."\",\"cognome\":\"".$cognome."\",\"email\":\"".$email."\",\"username\":\"".$username."\"}";

    }else{

	    echo "{\"id\":\"-1\"}";
    }
    $connessione->close();
 ?>
