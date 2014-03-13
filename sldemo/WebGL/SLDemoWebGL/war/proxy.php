<?php

	//print_r($_SERVER);
	//return;

	//$host=gethostbyaddr($_SERVER["REMOTE_ADDR"]);
	//if($host!="ami.dis.ulpgc.es" && $host!="amiserver.dis.ulpgc.es" && $host!="amifire.dis.ulpgc.es") {
	//	echo "Origen no autorizado";
	//	return;
	//}

	function getClientHeaders() {
		$headers=array();
		foreach($_SERVER as $header => $value) {
			if(substr($header,0,5) == "HTTP_") {
				$header=str_replace('_',' ',substr($header,5));
				$header=str_replace(' ','-',ucwords(strtolower($header)));
				$headers[$header]=$value;
			}
		}
		return $headers;
	}
	
	//URL de la petición original que generó el navegador, incluyendo la
	//llamada al proxy:
	$fullURL= 'http://'.$_SERVER['HTTP_HOST'].$_SERVER['REQUEST_URI'];
	
	//URL de la petición al servidor WMS
	$url=substr($fullURL,strpos($fullURL,"?url=")+strlen("?url="));
	
	//Tiempo de vida de un objeto en la cache, en segundos:
	$maxTTL=31536000;
	
	//Se obtienen las cabeceras que mandó el navegador:
	$original_headers=getClientHeaders();
	
	//Este array va a contener las cabeceras modificadas por el proxy
	//que finalmente llegarán al servidor WMS:
	$request_headers=array();
	
	//$IfModifiedPresent=false;
	
	foreach($original_headers as $header => $value) {
		if($header=="Host") {
			$parsedUrl=parse_url($url);
			$value=$parsedUrl["host"];
		}
		/*elseif($header=="If-Modified-Since") {
			$IfModifiedPresent=true;
			$elapsedTime=strtotime($now)-strtotime($value);
		}*/
		elseif($header=="User-Agent" || $header=="Accept-Encoding"/* || $header=="Referer"*/) continue;
		$request_headers[]="$header: $value";
	}
	
	$response_headers=array();
	
	$response_status="";
		
	$content="";
	
	//$expiredCache=false;
	
	$statusCode=200;
	
	//Caso en el que el cliente tiene una copia del objeto en su caché
	/*if($IfModifiedPresent) {
		
		//Primero hay que comprobar si la copia sigue vigente;
		if($elapsedTime>$maxTTL) $expiredCache=true;
		else {
		
			//Si sigue vigente, se indica al cliente que no es
			//necesario refrescar
			$response_status="HTTP/1.1 304 Not Modified";
		}
		
	}*/
	
	//Si el cliente no indica la versión de su última copia en caché,
	//o la que tiene ha expirado segun la comprobacion realizada arriba
	//se solicita una nueva versión de la imagen al servidor real
	//if(!$IfModifiedPresent || ($IfModifiedPresent && $expiredCache)) {
		
		//Se prepara curl para enviar la petición al servidor
		$ch=curl_init();
	
		curl_setopt($ch, CURLOPT_HEADER, 1);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($ch, CURLOPT_HTTPHEADER, $request_headers);
		curl_setopt($ch, CURLOPT_URL, $url);
		
		$response=curl_exec($ch);
		
		curl_close($ch);
		
		$header_length=0;
		
		$get_status=true;
		
		$responseStatus="";
		
		$validContent=false;
		
		foreach(explode("\r\n",$response) as $value) {
			if($get_status) {
				$responseStatus=$value;
				$show_status=false;
			}
			$header_length+=(strlen($value)+2);
			if(stripos($value,"Content-Type")===0) {
				//if(stripos($value,"image/")>0) {
					$validContent=true;
					$response_headers[]=$value;
				//}
			}
			if(stripos($value,"Content-Length")===0) $response_headers[]=$value;
			if(strlen($value)==0) break;
		}
		
		/*if(!$validContent) {
			$response_status="HTTP/1.1 404 Not Found";
			$content="Data error";
		}
		else {*/
						    
			$response_status="HTTP/1.1 200 OK";
		
			$content=substr($response,$header_length);
		
			$response_headers[]="Content-Length: ".strlen($content);
			
			$response_headers[]="Cache-Control: max-age=$maxTTL";
		//}
		
	//}
		
	header($response_status,true);
	
	foreach($response_headers as $value) header($value,true);
	
	if(strlen($content)>0) echo $content;

?>
