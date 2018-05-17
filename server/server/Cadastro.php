<?php
require 'Conexaobd.php';
if($_SERVER["REQUEST_METHOD"]=="POST"){
    
    createCadastro();
}


function createCadastro()
{
    global $connect;
    
    $nome = $_POST['nome'];
	$endereco = $_POST['endereco'];
	$numerocpf = $_POST['numerocpf'];
	$numerosus = $_POST['numerosus'];
	$email = $_POST['email'];
	$senha = $_POST['senha'];
	
	$query = "INSERT INTO `paciente`(`nome`,`endereco`,`cpf`,`numerosus`,`email`,`senha`)                     
	VALUE ('$nome','$endereco','$numerocpf','$numerosus','$email','$senha')";
	
	mysqli_query($connect, $query) or die (mysqli_error($connect));
	mysqli_close($connect);
}
?>