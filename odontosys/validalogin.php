<?php
session_start();
//Incluindo a conexão com banco de dados
include_once("init.php");
//O campo usuário e senha preenchido entra no if para validar
if((isset($_POST['login'])) && (isset($_POST['senha']))){
    $usuario = mysqli_real_escape_string($conn, $_POST['login']); //Escapar de caracteres especiais, como aspas, prevenindo SQL injection
    $senha = mysqli_real_escape_string($conn, $_POST['senha']);
    
    //Buscar na tabela usuario o usuário que corresponde com os dados digitado no formulário
    $result_usuario = "SELECT * FROM usuario WHERE login = '$usuario' AND senha = '$senha'";
    $resultado_usuario = mysqli_query($conn, $result_usuario);
    $resultado = mysqli_fetch_assoc($resultado_usuario);
    
    //Encontrado um usuario na tabela usuário com os mesmos dados digitado no formulário
    if(isset($resultado)){
        $_SESSION['usuarioId'] = $resultado['id'];
        $_SESSION['usuariologin'] = $resultado['login'];
        $_SESSION['usuariosenha'] = $resultado['senha'];
        $_SESSION['usuarioNiveisAcessoId'] = $resultado['tipo_usuario_id'];
        if($_SESSION['usuarioNiveisAcessoId'] == "1"){
            header("Location: index.php");
            }
            elseif($_SESSION['usuarioNiveisAcessoId'] == "2"){
            header("Location: indexfuncionario.php");
        }
        //Não foi encontrado um usuario na tabela usuário com os mesmos dados digitado no formulário
        //redireciona o usuario para a página de login
    }else{
        //Váriavel global recebendo a mensagem de erro
        $_SESSION['loginErro'] = "Usuário ou senha Inválido";
        header("Location: login.php");
    }
    //O campo usuário e senha não preenchido entra no else e redireciona o usuário para a página de login
}else{
    $_SESSION['loginErro'] = "Usuário ou senha inválido";
    header("Location: login.php");
}
?>