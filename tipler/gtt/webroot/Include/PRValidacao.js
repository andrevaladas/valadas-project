var reLetra 	= /*/\D/*/ /^[^\d*?]+$/
var reDigito	= /^\d/
var reCPF	= /^(\d{3})\W?(\d{3})\W?(\d{3})\W?(\d{2})$/
var fsCPF	= "$1.$2.$3-$4"
var feCPF	= "$1$2$3$4"
var reCGCMF	= /^(\d{8})\W?(\d{4})\W?(\d{2})$/
var fsCGCMF	= "$1-$2/$3"
var feCGCMF	= "$1$2$3"
var reDDMMAAAA	= /^(([0][1-9])|([1,2]\d)|([3][0,1]))\W?(([0][1-9])|([1][0-2]))\W?([1-9][0-9]{3})$/
var fsDDMMAAAA	= "$1/$5/$8"
var reDDMMAA	= /^(([0][1-9])|([1,2]\d)|([3][0,1]))\W?(([0][1-9])|([1][0-2]))\W?([0-9][0-9]{1})$/
var fsDDMMAA	= "$1/$5/$8"
//*** Incluído por Régis Steigleder em 01/2006
var reMMAAAA	= /^(([0][1-9])|([1][0-2]))\W?([1-9][0-9]{3})$/
var fsMMAAAA	= "$1/$4"
var reMMAA		= /^(([0][1-9])|([1][0-2]))\W?([0-9][0-9]{1})$/
var fsMMAA		= "$1/$4"
//***
var reCEP 	=/^\d{8}$/;
var reDigitos 	=/^\d+$/;

//*** Valida valores e limpa campos
//    Autor: André Valadas
function getValue(value)
{
	return (value == "null" || value == null) ? "" : value;
}
function getValueDef(value, vdefault)
{
	return (value == "null" || value == null || value == "") ? vdefault : value;
}
function getValueDif(value, vmask, vdefault)
{
	if (getValue(value) != "" && vmask != null)
	{
		return (value == vmask) ? vdefault : value;
	} else return getValue(value);
}

//*** Formatador por Mascáras
// Exemplos:
// *-- CPF --*
// OnKeyPress="formatar(this, '###.###.###-##')"
// *-- CEP --*
// OnKeyPress="formatar(this, '#####-###')"
function formatar(src, mask) 
{
	var i = src.value.length;
	var saida = mask.substring(0,1);
	var texto = mask.substring(i);
	if (texto.substring(0,1) != saida) 
	{
		src.value += texto.substring(0,1);
	}
}

function getDigitoVerificador(value, delimitador, modulo)
{
	//*** Validações
    if (getValue(value) == "")
    	return "";
    if (getValue(delimitador) == "")
    	delimitador = "-";
    if (value.replace(delimitador,"").length != 5)
    	return "";
    if (getValue(modulo) == "" || modulo < 1)
    	modulo = Number(10);
    	
	//*** Variáveis
    var calculo = 0, resto = 0, soma = 0;
	for (var i=0; i < value.length; i++)
    {
        if (i % 2 != 0)
        {
            soma = 2 * Number(value.substring(i, i+1));
            while (soma >= 10)
            {
                var strSoma = soma.toString();
                soma = 0;
                for (var x=0; x<strSoma.length; x++)
                    soma += Number(strSoma.substring(x, x+1));
            }
            calculo += Number(soma);
        } else calculo += Number(value.substring(i, i+1));
    }
    resto = calculo % modulo;
    var toReturn = modulo - resto;
    if (toReturn >= 10)
        toReturn = 0;
    return "-"+toReturn.toString();
}

function doValidaDigitoVerificador(value, delimitador, modulo)
{
	//*** Validações
    if (getValue(value) == "")
    	return value;
    if (getValue(delimitador) == "")
    	delimitador = "-";
    if (value.replace(delimitador,"").length != 6)
    	return value;
	if (getValue(modulo) == "" || modulo < 1)
    	modulo = Number(10);
    	
    var toReturn = getDigitoVerificador(value.substring(0,5), delimitador, modulo);
    if (value.charAt(value.length-1) != toReturn.charAt(toReturn.length-1))
    {
    	alert("Digito Verificador não confere!");
    	return value.substring(0,6);
    } else return value;
}
		
//*** Valida Numero Máximo de Caracteres
// Exemplo:
// onkeypress="return validaCaracter(this.value, 200);"
function validaCaracter(value, maxCaracter) {
	//*** Verifica Variavel
	if (maxCaracter == null)
		maxCaracter = Number(255);
	value = value.toString();
	//*** Verifica tamanho do Texto
	if (maxCaracter <= value.length) {
		alert("Atenção! Máximo de Caracteres "+maxCaracter+"!");
		return false;
	} else return true;
}

function validaCaracterNoAlert(value, maxCaracter) {
	//*** Verifica Variavel
	if (maxCaracter == null)
		maxCaracter = Number(255);
	value = value.toString();
	//*** Verifica tamanho do Texto
	if (maxCaracter <= value.length) {
		return false;
	} else return true;
}

function formataTeclaSemZero(e){

if (document.all) // Internet Explorer
	var tecla = event.keyCode;
else if(document.layers) // Nestcape
	var tecla = e.which;
if (tecla > 48 && tecla < 58) // numeros de 0 a 9
	return true;
else{
if (tecla != 8) // backspace
	event.keyCode = 0;
	//return false;
else
return true;
}
}

function formataTeclaSomenteLetras(e)
{

if (document.all) // Internet Explorer
	var tecla = event.keyCode;
else if(document.layers) // Nestcape
	var tecla = e.which;

if (tecla >= 65 && tecla <= 90) // numeros de A a Z
	tecla = tecla;
else
if (tecla >= 97 && tecla <= 122) // numeros de a a z
	tecla = tecla;
else{
if (tecla != 8) // backspace
	event.keyCode = 0;
	//return false;
else
return true;
}
}

function formataTeclaSemAspas(e){

if (document.all) // Internet Explorer
	var tecla = event.keyCode;
else if(document.layers) // Nestcape
	var tecla = e.which;


  if (tecla != 8 && (tecla == 34 || tecla == 39) )
	event.keyCode = 0;
	//return false;
  else
        return true;

}

function formataTeclaDouble(e)
{
if (document.all) // Internet Explorer
	var tecla = event.keyCode;
else if(document.layers) // Nestcape
	var tecla = e.which;
if (tecla > 47 && tecla < 58) // numeros de 0 a 9
	return true;
else{
if (tecla != 8 && tecla != 46) // backspace
	event.keyCode = 0;
	//return false;
else
return true;
}
}



function formataValorDigitosBT(numero)
{

if ((numero==null) || (numero==''))
{
numero = '';
return numero;
}
numero_formatado = (parseFloat(numero) * 1000)/10;

numero_formatado  = parseInt(numero_formatado);

numero_ultimo_digito = numero * 1000;

numero_ultimo_digito = parseInt(numero_ultimo_digito);

string_numero = numero_ultimo_digito.toString();

ultimo_digito =
string_numero.substring(string_numero.length-1,string_numero.length);

if (ultimo_digito>=5)
{
numero_formatado = numero_formatado + 1;

}

numero_formatado = numero_formatado.toString();
var tam = numero_formatado.length;
numero_formatado2 = (parseFloat(numero) * 1000)/10;
numero_formatado3 = parseInt(numero_formatado);
if (numero_formatado2 != numero_formatado3){
	//return numero;
	}

for (var i = 0; i < numero.length; i++)
	if (numero.charAt(i) == ",") return numero;

if ( tam <= 1 ){
numero_formatado = '0,0' + numero_formatado.substr( tam - 2, tam ); }

if ( tam == 2 ){
numero_formatado = '0,' + numero_formatado.substr( tam - 2, tam ); }

if ( (tam > 2) && (tam <= 5) ){
numero_formatado =
numero_formatado.substr( 0, tam - 2 ) + ',' +
numero_formatado.substr( tam - 2, tam ); }

if ( (tam >= 6) && (tam <= 8) ){
numero_formatado =
numero_formatado.substr( 0, tam - 5 ) + '.' +
numero_formatado.substr( tam - 5, 3 ) + ',' +
numero_formatado.substr( tam - 2, tam ) ;}

if ( (tam >= 9) && (tam <= 11) ){
numero_formatado =
numero_formatado.substr( 0, tam - 8 ) + '.' +
numero_formatado.substr( tam - 8, 3 ) + '.' +
numero_formatado.substr( tam - 5, 3 ) + ',' +
numero_formatado.substr( tam - 2, tam ) ; }

if ( (tam >= 12) && (tam <= 14) ){
numero_formatado =
numero_formatado.substr( 0, tam - 11 ) + '.' +
numero_formatado.substr( tam - 11, 3 ) + '.' +
numero_formatado.substr( tam -  8, 3 ) + '.' +
numero_formatado.substr( tam -  5, 3 ) + ',' +
numero_formatado.substr( tam -  2, tam ) ; }

if ( (tam >= 15) && (tam <= 17) ){
numero_formatado =
numero_formatado.substr( 0, tam - 14 ) + '.' +
numero_formatado.substr( tam - 14, 3 ) + '.' +
numero_formatado.substr( tam - 11, 3 ) + '.' +
numero_formatado.substr( tam -  8, 3 ) + '.' +
numero_formatado.substr( tam -  5, 3 ) + ',' +
numero_formatado.substr( tam -  2, tam ) ;}

if ( (tam >= 18) && (tam <= 20) ){
numero_formatado =
numero_formatado.substr( 0, tam - 17 ) + '.' +
numero_formatado.substr( tam - 17, 3 ) + '.' +
numero_formatado.substr( tam - 14, 3 ) + '.' +
numero_formatado.substr( tam - 11, 3 ) + '.' +
numero_formatado.substr( tam -  8, 3 ) + '.' +
numero_formatado.substr( tam -  5, 3 ) + ',' +
numero_formatado.substr( tam -  2, tam ) ;}

if ( (tam >= 21) && (tam <= 23) ){
numero_formatado =
numero_formatado.substr( 0, tam - 20 ) + '.' +
numero_formatado.substr( tam - 20, 3 ) + '.' +
numero_formatado.substr( tam - 17, 3 ) + '.' +
numero_formatado.substr( tam - 14, 3 ) + '.' +
numero_formatado.substr( tam - 11, 3 ) + '.' +
numero_formatado.substr( tam -  8, 3 ) + '.' +
numero_formatado.substr( tam -  5, 3 ) + ',' +
numero_formatado.substr( tam -  2, tam ) ;}

if (numero_formatado == "N,aN")
{
	numero_formatado = "";
}

return numero_formatado;
}


function formataValorDigitosTB(numero)
{
if ((numero==null) || (numero==''))
{
numero = 0.000000;
return numero;
}

for (j = 1; j <= 3; j++) {

	string_ponto = numero.toString()
	posicao_ponto = string_ponto.indexOf(".")
	if (posicao_ponto!= -1)
	{
	numero = string_ponto.replace("." , "");
	}
}


string_virgula = numero.toString()
posicao_virgula = string_virgula.indexOf(",")
if (posicao_virgula!= -1)
{
numero = string_virgula.replace("," , ".");
}


numero_formatado = (parseFloat(numero) * 1000)/10;
numero_formatado = parseInt(numero_formatado);
numero_ultimo_digito = numero * 1000;
numero_ultimo_digito = parseInt(numero_ultimo_digito);

string_numero = numero_ultimo_digito.toString();

ultimo_digito =
string_numero.substring(string_numero.length-1,string_numero.length);

if (ultimo_digito>=5)
{
numero_formatado = numero_formatado + 1;
}
numero_formatado = numero_formatado / 100;
return parseFloat(numero_formatado);
}


// Funcao que checa se os campos do tipo TEXT obrigatorios da selecao e faz alguns testes de consistencia.
function valInputTextSelecao(oCampo) {
	for (i = 0; i < valInputTextSelecao.arguments.length; i++) {
		oCampo = valInputTextSelecao.arguments[i];
		if (oCampo.value && !reLetra.test(oCampo.value)) {
			alert ("Se este campo estiver preenchido, deve ter apenas letras ou espaço.");
			oCampo.focus();
			return false;
		}
	}
	return true;
}


// Funcao que checa se os campos de Nome do tipo TEXT obrigatorios da selecao e faz alguns testes de consistencia.
function valInputTextNome() {
	var bOK = false;
	for (i = 0; i < valInputTextNome.arguments.length; i++) {
		oCampo = valInputTextNome.arguments[i];
		if (oCampo.value) {
			nomes = oCampo.value.split(" ");
			for(j = 0; j < nomes.length; j++) {
				if (reLetra.test(nomes[j]) && nomes[j].length > 1) bOK = true;
				else if (!reLetra.test(nomes[j])) {
					bOk = false;
					break;
				}
			}
			if (!bOK) {
				alert("Se este campo estiver preenchido, deve ter pelo menos uma palavra com duas letras válidas.");
				oCampo.focus();
				return false;
			}
		}
	}
	return true;
}


// valida se o campo soh tem dados numericos
function formataNumerico(oCampo,sAsterisco) {
	if (sAsterisco) while(oCampo.value.search(/[^\d\*]/) != -1) oCampo.value = oCampo.value.replace(/[^\d\*]/,"");
	else while(oCampo.value.search(/\D/) != -1) oCampo.value = oCampo.value.replace(/\D/,"");
}


/* funcao que valida o formato do email.
 * 'exclude' checa 5 condicoes:
 * a) caracteres que nao podem estar no endereço
 * b) caracteres que nao podem estar no começo
 * c) & d) caracteres que nao podem estar juntos
 * e) se nao ha mais de um '@'
 * 'check' checa se ha pelo menos um '@', seguido de pelo menos um '.'
 * 'checkend' checa se o endereco termina com 2 or 3 caracteres alpha */
function valEmail(oCampo,iAba) {
	var exclude=/[^@\-\.\w]|^[_@\.\-]|[\._\-]{2}|[@\.]{2}|(@)[^@]*\1/;
	var check=/@[\w\-]+\./;
	var checkend=/\.[a-zA-Z]{2,3}$/;
	if (oCampo.value != "") {
		if (((oCampo.value.search(exclude) != -1) || (oCampo.value.search(check)) == -1) || (oCampo.value.search(checkend) == -1)) {
			if (iAba != null) Dados.oAbas.selecionaTabDefault(iAba);
			alert("Formato de e-mail inválido.");
			oCampo.focus();
			return false;
		}
	}
	return true;
}


/* Funcao que checa se os campos obrigatorios nao estao vazios.
 * O primeiro argumento (posicao zero) eh o [object] formulario;
 * Depois, as posicoes impares sao os nomes dos campos que serao testados e
 * as posicoes pares serao as mensagens de alerta que o usuario receberah.
 * A mensagem respectiva de cada campo estah na posicao posterior a do campo.
 * A funcao ainda retorna o foco ao campo que estah incorreto. */
function valCamposObrigatorios() {
	var i = 1;
	while (valCamposObrigatorios.arguments[i] != null) {
		oCampo = valRetornaCampo(valCamposObrigatorios.arguments[0],valCamposObrigatorios.arguments[i]);
		if (oCampo.value == "") {
			alert (valCamposObrigatorios.arguments[i+1]);
			if (oCampo.type != "hidden" && oCampo.disabled == false) oCampo.focus();
			return false;
		} i += 2;
	}
	return true;
}


/* Funcao que checa se os campos obrigatorios nao estao vazios em modulos com Abas.
 * O primeiro argumento (posicao zero) eh o [object] formulario;
 * O segundo argumento (posicao um) eh o [object] aba onde o campo estah;
 * A funcao ainda retorna o foco ao campo que estah incorreto. */
function valCamposObrigatoriosAba() {
	var i = 1;
	while (valCamposObrigatoriosAba.arguments[i] != null) {
		oCampo = valRetornaCampo(valCamposObrigatoriosAba.arguments[0],valCamposObrigatoriosAba.arguments[i]);
		if (oCampo.value == "") {
			Dados.oAbas.selecionaTabDefault(valCamposObrigatoriosAba.arguments[i+2]);
			alert (valCamposObrigatoriosAba.arguments[i+1]);
			if (oCampo.type != 'hidden' && oCampo.disabled == false) oCampo.focus();
			else if (oCampo.disabled == true) {
				valRetornaProx(Dados.document.forms[0],oCampo).focus();
			}
			return false;
		} i += 3;
	}
	return true;
}


/* recebe o form e o campo e seta o foco no proximo campo
 * Chamada pra esta funcao no input: onKeyPress="parent.valAvancaFocus(this.form,this)" */
function valAvancaFocus(oForm,oCampo) {
	if (oCampo.value.length >= oCampo.maxLength)
		valSetaFocus(oForm, valRetornaProx(oForm,oCampo,true));
	return;
}

// Recebe o form e o campo (object) e seta o foco nele
function valSetaFocus(oForm,oCampo) {
	if (oCampo) oCampo.focus();
	return;
}

// Recebe o nome do campo (string) e retorna o objeto campo (object)
function valRetornaCampo(oForm,sCampo) {
	for (i=0; i<oForm.elements.length; i++) {
		if (oForm.elements[i].name == sCampo) return (oForm.elements[i]);
	}
	return false;
}

// Procura nos elementos do form o campo passado como parametro e retorna o proximo campo
function valRetornaProx(oForm,oCampo) {
	for (i=0; i<oForm.elements.length; i++) {
		if (oForm.elements[i] == oCampo) {
			for (j=i+1; j<oForm.elements.length; j++) {
				if ((oForm.elements[j] != null) && (oForm.elements[j].type != 'hidden') && (oForm.elements[j].disabled == false))
					return (oForm.elements[j]);
			}
		}
	}
	return false;
}

/* Funcao que pega todos os elementos do formulario e verifica se sao checkbox
 * e se estao marcados. Se estao marcados, seta o valor do campo hidden pra 'S'.
 * OBS.: o nome do campo hidden eh o nome correto e o nome do checkbox tem duas
 * letras a mais antes. No caso foi adotado 'L_' de Lixo. */
function valSetaCheckBox(oForm) {
	for (i=0; i<oForm.elements.length; i++) {
		oCheckBox = oForm.elements[i];
		if (oCheckBox.type == "checkbox" && oCheckBox.checked) {
			oHidden = valRetornaCampo(oForm,oCheckBox.name.substring(2,oCheckBox.name.length));
			oHidden.value = "S";
		}
	}
	return true;
}

// Recebe a posicao do form e retorna o object form correspondente
function valRetornaForm(posForm) {
	for (i=0; i<Dados.document.forms.length; i++) {
		if (Dados.document.forms[i] == Dados.document.forms[posForm])
			return (Dados.document.forms[i]);
	}
	return false;
}

/* funcao que limita a quantidade de caracteres de um TEXTAREA
 * chamar a funcao com estes eventos: onKeyUp E onKeyDown
 * parametros: campo e o tamanho maximo */
function valLimitaTextArea(oCampo,nMax) {
	if (oCampo.value.length == 1 && oCampo.value.substring(0,1) == " ")
		oCampo.value = "";
	else if (oCampo.value.length > nMax)
		oCampo.value = oCampo.value.substring(0,nMax);
	return;
}

//seta a flag bAlteracao pra indicar se o usuario editou algum campo
var bAlteracao = false;
function valVerificaAlteracao(sArg) {
	if (valVerificaAlteracao.arguments.length == 0) return bAlteracao;
	else bAlteracao = sArg;
}

//seta a flag bLista pra indicar se o sistema passou pela lista
var bLista = false;
function valVerificaLista(sArg) {
	if (valVerificaLista.arguments.length == 0) return bLista;
	else bLista = sArg;
}


/* funcao que valida se no campo tem apenas digitos
 * parametros: 1- campo(object); 2- mensagem de erro */
function valDigitos(oCampo,msg) {
	if (!reDigitos.test(oCampo.value)) {
		alert (msg + " deve conter apenas números.");
		Dados.achaAbaErro(oCampo.name.toString());
		return false;
	}
	return true;
}


/* Recebe o campo [object] e verifica se ele estah vazio, se estah, retorna true pq o CEP nao eh
 * obrigatorio. Senao, verifica se tem exatos 8 digitos, nesse caso, dah um alert e seta o foco nele. */
function valCEP(oCampo,iAba) {
	if (oCampo.value != "") {
		if (!reCEP.test(oCampo.value)) {
			if (iAba != null) Dados.oAbas.selecionaTabDefault(iAba);
			alert ("O CEP deve conter 8 números!");
			oCampo.focus();
			return false;
		}
	}
	return true;
}

/* valida a funcao de RG, quando tem os outros campos como UF, orgao, etc...
 * parametros:
 * 1-RG; 2-Data; 3-UF; 4-Orgao; 5-Aba */
function valDadosRG() {
	var numPreenchidos = 0;
	var campoVazio;
	var aba = valDadosRG.arguments[4];
	for (i=3; i>=0; i--) { // sao 4 campos sobre o RG
		if (valDadosRG.arguments[i].value != "") numPreenchidos += 1;
		else campoVazio = valDadosRG.arguments[i];
	}
	if (numPreenchidos != 4 && numPreenchidos != 0) {
		if (aba != "undefined" && aba != null) Dados.oAbas.selecionaTabDefault(aba);
		alert("Informe todos os campos referentes ao RG.");
		campoVazio.focus();
		return false;
	}
	if (!valData(valDadosRG.arguments[1],"Data de emissão inválida.",2)) return false;
	if (!valPeriodoData(valDadosRG.arguments[1],null,"Data de emissão tem que ser menor ou igual à data de hoje.")) return false;
	return true;
}

/* funcao utilizada pela valData(...)
 * se vier string - converte em espressão regular para aplicar 'g'=global */
function valFormat(instr, reBusca, sTroca, umavez, ignorecase) {
	var x = String(typeof reBusca);
	if (x.toUpperCase().indexOf('STRING')>=0)
		reBusca = new RegExp(reBusca,((ignorecase)?"i":"")+((umavez)?"":"g"))
	return instr.replace(reBusca,sTroca);
}
//********************************************************************************************
// Valida datas
// true = ok
// false = com erro
// 0 = vazio
// Alterado por Régis Steigleder em 01/2006
// Para aceitar validacao de mes/ano (mm/aaaa ou mm/aa)
// quando itipo = 3
function valData(field,bmsg,itipo) {
  	if (field.value==""){
  		return 0
  	};
	var atipo=["data","dd/mm/aa","dd/mm/aaaa","mes/ano"]
	itipo=(itipo)?itipo:0 //0=qualquer, 1=dmaa,2=dmaaaa,3=maaaa
	var bDDMMAA 	=false
	var bDDMMAAAA	=false
	var bMMAAAA		=false
	var bMMAA 		=false
	if (itipo==0 || itipo == 1) {
		bDDMMAA = reDDMMAA.test(field.value)
	}
	if (itipo==0 || itipo==2) {
		bDDMMAAAA = reDDMMAAAA.test(field.value)
	}
	if (itipo==3) {
		bMMAAAA = reMMAAAA.test(field.value)
		bMMAA	= reMMAA.test(field.value)
	}
	if ((!bMMAA)&&(!bMMAAAA)&&(!bDDMMAA)&&(!bDDMMAAAA) ) {// formato não reconhecido
	 	 if (bmsg) {
	 	 	alert ("Conteúdo informado não reconhecido como sendo "+atipo[itipo]+"\nVerifique sua digitação")
	 	 	field.value = '';
	 	 }
		 field.select();
		 field.focus();
		 return false
	 }
  	var dia0,mes0,ano0
  	var auxData;
  	var dData=""
	if (bDDMMAAAA) {
		dData = impFormat(field.value,reDDMMAAAA,fsDDMMAAAA);
	} else 
	if (bDDMMAA){
		dData = impFormat(field.value,reDDMMAA,fsDDMMAA);
	} else
	if (bMMAAAA){
		dData = impFormat(field.value,reMMAAAA,fsMMAAAA);
	} else
	if (bMMAA){
		dData = impFormat(field.value,reMMAA,fsMMAA);
	} 

  	dma=dData.split("/");
	if (bDDMMAA) {
		if (String(dma[2]).length == 2) {
			if (dma[2].substring(0, 1) == "0") {
				dma[2] = dma[2].substring(1);
			}
		}
		dma[2] = (parseInt(dma[2]) < 20) ? parseInt(dma[2]) + 2000 : parseInt(dma[2]) + 1900;
	} if (bMMAA){
		if (String(dma[1]).length == 2) {
			if (dma[1].substring(0, 1) == "0") {
				dma[1] = dma[1].substring(1);
			}
		}
		dma[1] = (parseInt(dma[1]) < 20) ? parseInt(dma[1]) + 2000 : parseInt(dma[1]) + 1900;
	} // janelamento de data
  	dData=dma.join("/")
	// Se itipo = 3 (mm/aa ou mm/aaaa encerrra a rotina aqui pois não há necessidade de verificar se é uma data válida)			
	if (itipo==3) {
		field.value = dData
		return true
	}

	var obj = new Date(dma[2], dma[1]-1, dma[0])//cria data no browser
  	dia0 = "00" + obj.getDate()
  	mes0 = "00" + (obj.getMonth()+1)
  	ano0 = "0000" + obj.getFullYear()

  	dia0 = dia0.substring(dia0.length-2)
  	mes0 = mes0.substring(mes0.length-2)
  	ano0 = ano0.substring(ano0.length-4)
	
  	auxData = dia0 + "/"+  mes0 + "/"+ ano0
	
	if (auxData != dData) {
		if (bmsg) alert("Data incorreta\nVerifique sua Digitação")
		field.select();field.focus()
		return false
	} else {
		field.value = dia0 + "/" + mes0 + "/" + ((itipo == 1) ? ano0.substring(ano0,2) : ano0)
		return true
	}
  }

//********************************************************************************************
// Valida horas
// true = ok
// false = com erro
// 0 = vazio
  function valHora(field,bmsg) {

  	if (field.value=="") {
		return 0;
  	}
	var vHora = field.value.substring(0,2)
	var vMinuto = field.value.substring(3,5)
	if (vHora > 23 || vMinuto > 59 || vMinuto =="" || vHora =="")
	if(bmsg) {
		alert ("Conteúdo informado não reconhecido como sendo hora válida. \nVerifique sua digitação");
		field.value = "";
		field.select();
		field.focus();
		return false;
	} else {
		field.value=vHora+":"+vMinutos
		return true;
	}
  }

/* formata o campo de hora com ':'
 * parametros:
 * 1. sCampo = nome do campo de valor
 * 2. tammax = tamanho maximo de digitos de valor (tirando os pontos e virgulas)
 * 3. teclapres = event (para pegar o evento de teclado) */
function formataHora(sCampo,tamMax,teclapres) {
	var tecla = teclapres.keyCode;
	vr = sCampo.value;
	vr = troca(vr, ":", "" );
	tam = vr.length + 1;
	if ( tecla != 67 && tecla != 86 && tecla != 17 && tecla != 88 && tecla != 8 && tecla != 9 && tecla != 46 && tecla != 15 && tecla != 16 && tecla != 35 && tecla != 36 && tecla != 37 && tecla != 38 && tecla != 39){
		if ( tam > 2 && tam < 5 )
			sCampo.value = vr.substr( 0, tam - 2  ) + ':' + vr.substr( tam - 2, tam );
	}
}


//substitui todas as ocorrencias de uma string expressão regular por outra
function impFormat(instr, reBusca, sTroca,umavez,ignorecase) {
	var x= String(typeof reBusca); // Se vier string - converte em espressão regular para aplicar 'g'=global
    if (x.toUpperCase().indexOf('STRING')>=0){
	  	reBusca = new RegExp(reBusca,((ignorecase)?"i":"")+((umavez)?"":"g"))
	}
    return instr.replace(reBusca,sTroca);
}

  // valida se a segunda data eh maior que a primeira
  // null em qualquer um dos dois primeiros parametros significa a data de hoje
  // true se data1 <= data2
  // false se data2 > data1

function valPeriodoData(oData1,oData2,msg) {
	sData1 = oData1.value.toString();
	var data1 = retornaData(sData1);

	if (oData2 == null){
		var frameData = (parent.frames[2].Acoes)?"parent.frames[2].Acoes":"parent.Acoes"//verifica se está na tela principal
//se sim, há referência a janela principal
		var data2 = new Date(eval(frameData + ".sDataAux"));// converte para o formato Date()
	} else{
		sData2 = oData2.value.toString();
		var data2 = retornaData(sData2);// converte para o formato Date()
	}

	if (data1 > data2) {
		alert(msg);
		if (oData1 == null) Dados.achaAbaErro(oData2.name.toString());
		else Dados.achaAbaErro(oData1.name.toString());
		return false;
	}
	return true;
  }

    // valida se a primeira data eh maior que a segunda
  // null em qualquer um dos dois primeiros parametros significa a data de hoje
  // true se data1 >= data2
  // false se data2 < data1
  function valPeriodoDataFuturo(oData1,oData2,msg) {
	sData1 = oData1.value.toString();
	var data1 = retornaData(sData1);

	if (oData2 == null){
		var frameData = (parent.frames[2].Acoes)?"parent.frames[2].Acoes":"parent.Acoes"//verifica se está na tela principal
                //se sim, há referência a janela principal
		var data2 = new Date(eval(frameData + ".sDataAux"));// converte para o formato Date()
	} else{
		sData2 = oData2.value.toString();
		var data2 = retornaData(sData2);// converte para o formato Date()
	}

	if (data1 < data2) {
		alert(msg);
		if (oData1 == null) {
			Dados.achaAbaErro(oData2.name.toString());
		}
		else {
			Dados.achaAbaErro(oData1.name.toString());
		}
		return false;
	}
	return true;
  }

// função auxiliar para colocar a data no formato Date()
function retornaData(sData) {
	dia = sData.substr(0,2);
	mes = sData.substr(3,2);
	ano = sData.substr(6,4);
	return (new Date(ano,mes-1,dia));
}


// função auxiliar para saltaCampo e formataValor
function troca(str,chSrc,chDst) {
	var ret = "";
	//Percorrer a string toda rocando chSrc por chDst
	for (var i = 0; i<str.length; i++)
		if (str.charAt(i) == chSrc) ret = ret + chDst;
		else ret = ret + str.charAt(i);
	return ret;
}

/* elimina teclas de letras do campo, so permite numeros e pula para o proximo campo
 * Parametros:
 * 1. sCampo = nome do campo de valor
 * 2. oForm  = o form do campo
 * 3. tammax = tamanho maximo de digitos de valor (tirando os pontos e virgulas)
 * 4. teclapres = event (para pegar o evento de teclado) */
function saltaCampo(sCampo,oForm,tammax,teclap) {
	if (sCampo.value.length >= sCampo.maxLength){
		valSetaFocus(oForm, valRetornaProx(oForm,sCampo,true));
		return;
	}
	var teclap = teclap.keyCode;
	if (teclap == 39) return;
	vr = sCampo.value;
	if (teclap != 46 && teclap != 48 && teclap != 49 && teclap != 50 && teclap != 51 && teclap != 52 && teclap != 53 && teclap != 54 && teclap != 55
		&& teclap != 56 && teclap != 57 && teclap != 96 && teclap != 97 && teclap != 98 && teclap != 99 && teclap != 100 && teclap != 101 && teclap != 102
		&& teclap != 103 && teclap != 104 && teclap != 105 && teclap != 106 && teclap != 8 && teclap != 67 && teclap != 86 && teclap != 17 && teclap != 88
		&& teclap != 9 && teclap != 35 && teclap != 36 && teclap != 37 && teclap != 38 && teclap != 39 && teclap != 40 && teclap != 16 && teclap != 17 && teclap != 18) {
		sCampo.value = vr.substr( 0, vr.length - 1 );
	} else {
	 	vr = troca(vr, "-", "" );
	 	vr = troca(vr, "/", "" );
	 	vr = troca(vr, "/", "" );
	 	vr = troca(vr, ",", "" );
	 	vr = troca(vr, ".", "" );
	 	vr = troca(vr, ".", "" );
	 	vr = troca(vr, ".", "" );
	 	vr = troca(vr, ".", "" );
	 	tam = vr.length;
	 	if (teclap != 0 && teclap != 9 && teclap != 16  && teclap != 35 && teclap != 36 && teclap != 37 && teclap != 38 && teclap != 39 && teclap != 40) {
			if (tam == tammax) if (prox != "null") prox.focus();
		}
	}
}


/* formata o campo de data com barras '/'
 * parametros:
 * 1. sCampo = nome do campo de valor
 * 2. tammax = tamanho maximo de digitos de valor (tirando os pontos e virgulas)
 * 3. teclapres = event (para pegar o evento de teclado) 
 * Alterado por Régis Steigleder em 01/2006	
 * tamax = 8 data no formato dd/mm/aa ou dd/mm/aaaa
 * tamax = 6 data no formato mm/aa ou mm/aaaa	
*/
function formataData(sCampo,tamMax,teclapres) {
	var tecla = teclapres.keyCode;
	vr = sCampo.value;
	vr = troca(vr, ".", "" );
	vr = troca(vr, "/", "" );
	vr = troca(vr, "/", "" );
	tam = vr.length + 1;
	if ( tecla != 67 && tecla != 86 && tecla != 17 && tecla != 88 && tecla != 8 && tecla != 9 && tecla != 46 && tecla != 15 && tecla != 16 && tecla != 35 && tecla != 36 && tecla != 37 && tecla != 38 && tecla != 39) {
		if ( tam > 2 && tam < 5 )
			sCampo.value = vr.substr( 0, tam - 2  ) + '/' + vr.substr( tam - 2, tam );
		if ( tamMax > 6 )
			if ( tam >= 5 && tam <= 10) 
				sCampo.value = vr.substr( 0, 2 ) + '/' + vr.substr( 2, 2 ) + '/' + vr.substr( 4, 4 ); 
	}
}


/* formata o campo de valor com virgulas e pontos nos decimais
 * Parametros:
 * 1. sCampo = nome do campo de valor
 * 2. tammax = tamanho maximo de digitos de valor (tirando os pontos e virgulas)
 * 3. teclapres = event (para pegar o evento de teclado) */
function formataValor(sCampo,tammax,teclapres) {
	var tecla = teclapres.keyCode;
	vr = Dados.document.srcform[sCampo].value;
	vr = troca(vr, "/", "" );
	vr = troca(vr, "/", "" );
	vr = troca(vr, ",", "" );
	vr = troca(vr, ".", "" );
	vr = troca(vr, ".", "" );
	vr = troca(vr, ".", "" );
	vr = troca(vr, ".", "" );
	tam = vr.length;

	if (tam < tammax && tecla != 8) tam = vr.length + 1;
	if (tecla == 8 ) tam = tam - 1;

	if ( tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105 ) {
		if ( tam <= 2 ) {
	 		Dados.document.srcform[sCampo].value = vr ; }
	 	if ( (tam > 2) && (tam <= 5) ) {
	 		Dados.document.srcform[sCampo].value = vr.substr( 0, tam - 2 ) + ',' + vr.substr( tam - 2, tam ) ; }
	 	if ( (tam >= 6) && (tam <= 8) ) {
	 		Dados.document.srcform[sCampo].value = vr.substr( 0, tam - 5 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ; }
	 	if ( (tam >= 9) && (tam <= 11) ) {
	 		Dados.document.srcform[sCampo].value = vr.substr( 0, tam - 8 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ; }
	 	if ( (tam >= 12) && (tam <= 14) ) {
	 		Dados.document.srcform[sCampo].value = vr.substr( 0, tam - 11 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ; }
	 	if ( (tam >= 15) && (tam <= 17) ) {
	 		Dados.document.srcform[sCampo].value = vr.substr( 0, tam - 14 ) + '.' + vr.substr( tam - 14, 3 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ;}
	}

}


//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\

/* chamada pra esta funcao: if (parent.valCPFCGC(parent.valRetornaCampo(oForm,"CPF"),1,0))
 * dentro da funcao validaForm dos Xsl's de Incluir ou Alterar, depois de chamar a funcao
 * valCamposObrigatorios. Valida tanto CPF quanto CGC e no final poe os separadores. */
function valCPFCGC(oCampo,itipo,oForm) { // bmsg: se vai dar alert das mensagens
	var atipo=["CPF/CNPJ","CPF","CNPJ"]
	itipo=(itipo)?itipo:0 // 0=qualquer, 1=cpf,2=cgc
	var cpf='', cgc='', digito='',dg='', dgc='', digitoc='', k=0; i=0, j=0, soma=0, mt=0;
	var cpfcgc = oCampo.value;
	var bcpf=false
	var bcgc=false
	if (itipo==0||itipo==1) bcpf= reCPF.test(cpfcgc)	// valida tamanho e formato do CPF
	if (itipo==0||itipo==2) bcgc= reCGCMF.test(cpfcgc)	// valida tamanho e formato do CCG
	if ((!bcpf)&&(!bcgc)) {// formato não reconhecido
		alert ("Conteúdo informado não reconhecido como "+atipo[itipo]+"\nVerifique sua digitação")
		Dados.achaAbaErro(oCampo.name.toString());
		return false
	}
	// tirar separadores
	if (bcpf) {cpfcgc=cpfcgc.replace(reCPF,feCPF)}
	else {cpfcgc=cpfcgc.replace(reCGCMF,feCGCMF)}
	// valida entradas fáceis
	var cpferr = "0000000000011111111111222222222223333333333344444444444"+
					 "5555555555566666666666777777777778888888888899999999999"
	if ( cpferr.indexOf(cpfcgc) >= 0) { // informado campo de facil entrada
		/*if (bmsg)*/ alert ("Conteúdo não é aceito como " + atipo[itipo] + "\nVerifique sua digitação")
		Dados.achaAbaErro(oCampo.name.toString());
		return false
	}
	// tabela de pesos para colunas
	mult = [2,3,4,5,6,7,8,9,2,3,4,5,6,7,8,9];
	if (bcgc) {
		cgc = cpfcgc.substring(0,12);
		digito = cpfcgc.substring(12,14);
		for (j = 1; j <= 2; j++) {
			digitoc = impCalcDig11(cgc)
			if (digitoc == 10) {digitoc = 0}
			dgc +=digitoc;
			cgc+=digitoc;
		}
	}
	else {
		cpf = cpfcgc.substring(0,9);
		digito = cpfcgc.substring(9,11);
		for (j = 1; j <= 2; j++) {
			soma = 0;
			mt = 2;
			for (i = 8 + j; i >= 1; i--) {
				soma += parseInt(cpf.charAt(i-1),10) * mt;
				mt++;
			}
			dg = 11 - (soma % 11);
			if (dg > 9) {dg = 0};
			cpf += dg;
			dgc+=dg
		}
	}
	// digito inválido
	if (dgc != digito) {
		alert (((bcgc)?"CNPJ":"CPF")+" com dígitos inválidos\nVerifique sua digitação")
		Dados.achaAbaErro(oCampo.name.toString());
		return false
	}
	else {
		oCampo.value=valFormat(oCampo.value,((bcgc)?reCGCMF:reCPF),((bcgc)?fsCGCMF:fsCPF))
		return true
	}
}

//funcao do TSI
//digito módulo 11 - pode retornar digito 10  para verificação de aproveitamento
function impCalcDig11(numero) {
	var pesos=[2,3,4,5,6,7,8,9,2,3,4,5,6,7,8,9]
	var calc= 0 , x=''
	var numero=String(numero).split("")
	var limite=numero.length -1
	var result = 0
	var pso=0
	for (var i=limite; i>=0; i--) {
		x+="\npso:"+pesos[pso]+" * "+ numero[i] + " = " + (pesos[pso] * parseInt(numero[i]))
		calc += (pesos[pso++] * parseInt(numero[i]))
	}
	result = 11 - ((calc)%11)
	if (result == 11) {result = 0}
	return result
}

//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\

function keyDown(DnEvents) {
	key = Dados.event.keyCode;
	//if (key == 13) alert("Agora ele envia...");
}

function formataTeclaCampoValor(e)
{
	if (document.all) // Internet Explorer
		var tecla = event.keyCode;
	else if(document.layers) // Nestcape
		var tecla = e.which;
	if (tecla > 43 && tecla < 58) // numeros de 0 a 9
		return true;
	else{
		if (tecla != 8) // backspace
			event.keyCode = 0;
		else return true;
	}
}

function formataTeclaCampoNumerico(e)
{
	if (document.all) // Internet Explorer
		var tecla = event.keyCode;
	else if(document.layers) // Nestcape
		var tecla = e.which;
	if (tecla > 47 && tecla < 58) // numeros de 0 a 9
		return true;
	else{
		if (tecla != 8 && tecla!= 13) // backspace
			event.keyCode = 0;
		else return true;
	}
}

function upperCase() {
	if (document.all) { // Internet Explorer
		var evento = event;
		var keycode = evento.keyCode;
	} else {// Nestcape
		var evento = e;
		var keycode = evento.which;
	}
	// de [a-z] converte em [A-Z]
	if ((keycode >= 97 && keycode <= 122)
	|| keycode >= 224 && keycode <= 256) {
		keycode -= 32;
	}
	if (document.all) { // Internet Explorer
		evento.keyCode = keycode;
	} else {// Nestcape
		evento.which = keycode;
	}
}

function upperCaseCampo(campo) {
	var s = new String(campo.value);
	campo.value = s.toUpperCase();
}

//----------------------------------------------------------------------------------//

// Formata Valor BANCO >> TABELA
//*** Modificada por: André Valadas
//    Data: 21/01/2005
//    Correção: Formatação de valores NEGATIVOS, Nº de CASAS DECIMAIS passadas por parâmetro
function formataValorBT(numero, casasDec)
{
	numero = numero.toString();
	var index = numero.indexOf(",");
	if (index != -1)
	{
		numero = formataValorTB(numero, casasDec);
		numero = numero.toString();
	}

	//*** Variaveis para Casas Decimais - DEFAULT
	var auxCasas = 2;
	var Currency = "00";
	//*** Caso não seja informado o numero de casas decimais, ele retornará por default 2 casas decimais 
	if (casasDec != null && Number(casasDec) > 2)
	{
		if (Number(casasDec) == 3){
			auxCasas = 3;
			Currency = "000";
		} else if (Number(casasDec) == 4){
			auxCasas = 4;
			Currency = "0000";
		} else if (Number(casasDec) == 5){
			auxCasas = 5;
			Currency = "00000";
		} else if (Number(casasDec) == 6){
			auxCasas = 6;
			Currency = "000000";
		}
	}
	//*** Valida Número
	if ((numero==null) || (numero=='') || (numero=='null') || (numero.toString() == 'NaN') || (Number(numero.replace(",",".")) == 0))
	{
		return '0,'+Currency;
	}
	
	//*** Valores Negativos
	var sinalNegativo = null;
	if (numero.charAt(0) == "-")
	{
		sinalNegativo = '-';
		numero = numero.substr(1, numero.length);
	}
	//*** Converte
	var index_ponto = numero.indexOf(".");
	if (index_ponto != -1)
	{
		//*** Separa Numero e Casas Decimais
		Currency = (numero.substring(index_ponto+1,numero.length) + Currency);
		//*** Arredonda Valor
		if (Currency.length > auxCasas && Currency.charAt(auxCasas) >= 5)
		{
			Currency = Currency.substring(0,auxCasas-1) + (Number(Currency.charAt(auxCasas-1)) == 9 ? Number(Currency.charAt(auxCasas-1)) : Number(Currency.charAt(auxCasas-1))+1);
		} else Currency = Currency.substring(0,auxCasas);
		numero = numero.substring(0,index_ponto);
	}
	
	//*** Verifica tamanhos
	var tam = numero.length;
	if (tam <= 3)
	{
		numero = numero +','+ Currency;
	} else if (tam > 3 && tam <=6)
	{
		numero = numero.substr(0, tam -3) + "." + 
				 numero.substr(tam -3, tam) +','+ Currency;
	} else if (tam > 6 && tam <= 9)
	{
		numero = numero.substr(0, tam -6) + "." + 
				 numero.substr(tam -6, 3) + "." + 
				 numero.substr(tam -3, tam) +','+ Currency;
	} else if (tam > 9 && tam <= 12)
	{
		numero = numero.substr(0, tam -9) + "." + 
				 numero.substr(tam -9, 3) + "." + 
				 numero.substr(tam -6, 3) + "." + 
				 numero.substr(tam -3, tam) +','+ Currency;
	} else if (tam > 12 && tam <= 15)
	{
		numero = numero.substr(0, tam -12) + "." + 
				 numero.substr(tam -12, 3) + "." + 
				 numero.substr(tam -9, 3) + "." + 
				 numero.substr(tam -6, 3) + "." + 
				 numero.substr(tam -3, tam) +','+ Currency;
	} else if (tam > 15 && tam <= 18)
	{
		numero = numero.substr(0, tam -15) + "." + 
				 numero.substr(tam -15, 3) + "." + 
				 numero.substr(tam -12, 3) + "." + 
				 numero.substr(tam -9, 3) + "." + 
				 numero.substr(tam -6, 3) + "." + 
				 numero.substr(tam -3, tam) +','+ Currency;
	}
	//*** Seta sinal se número era negativo
	if (sinalNegativo != null)
	{
		numero = '-'+numero;
	}
	return numero;
} // formataValorBT

// Formata Valor TABELA >> BANCO
//*** Modificada por: André Valadas
//    Data: 21/01/2005
//    Correção: Formatação de valores NEGATIVOS, Nº de CASAS DECIMAIS passadas por parâmetro
function formataValorTB(numero, casasDec)
{
	numero = numero.toString();
	//*** Variaveis para Casas Decimais - DEFAULT
	var auxCasas = 2;
	//*** Caso não seja informado o numero de casas decimais, ele retornará por default 2 casas decimais 
	if (casasDec != null && Number(casasDec) > 2)
	{
		auxCasas = Number(casasDec);
	}
	//*** Valida Número
	if ((numero==null) || (numero=='') || (numero=='null') || (numero.toString() == 'NaN') || (Number(numero.replace(",",".")) == 0))
	{
		return 0;
	}
	//*** Valores Negativos
	var sinalNegativo = null;
	if (numero.charAt(0) == "-")
	{
		sinalNegativo = '-';
		numero = numero.substr(1, numero.length);
	}
	
	//*** Replace "." por "" e "," por "."
	while (numero.indexOf(".") != -1)
		numero = numero.replace("." , "");
	numero = numero.replace("," , ".");

	//*** Limita tamanho das Casas Decimais
	var index_ponto = numero.indexOf(".");
	if (index_ponto != -1)
	{
		if (index_ponto > 0)
		{
			//*** Arredonda Valor
			numero = arredondaValor(numero, auxCasas);
		} else numero = '0'+numero;
	}

	//*** Seta sinal se número era negativo
	if (sinalNegativo != null)
	{
		numero = '-'+numero;
	}
	return Number(numero);
} // formataValorTB

//*** ARREDONDA VALORES
//    por: André Valadas
//    Data: 18/04/2005
function arredondaValor(valor, decimal)
{
	if (decimal == null || Number(decimal) < 2)
		decimal = 2;
	var result = (valor * Math.pow(10, decimal));
	return Math.round(result) / Math.pow(10, decimal); 
}

function formataTeclaSemEnter(e){

	if (document.all) // Internet Explorer
    	var tecla = event.keyCode;
    else if(document.layers) // Nestcape
    	var tecla = e.which;
            
    if (tecla == 13 ){ 
        alert( "Tecla 'Enter' bloqueada para este campo!" );
        return false;
    }else
        return true;    
}

function formataTeclaSemEnterNoAlert(e){

	if (document.all) // Internet Explorer
    	var tecla = event.keyCode;
    else if(document.layers) // Nestcape
    	var tecla = e.which;
            
    if (tecla == 13 ){ 
        return false;
    } else return true;    
}

//*** Limpa campos do formulario (TEXT e HIDDEN somente)
//    Por  : Régis Steigleder
//    Data : 05/12/2005 
function limpaCamposForm(oForm) 
{
	for (i=0; i < oForm.elements.length ; i++) {	
		if (oForm.elements[i].type == "text" || oForm.elements[i].type == "hidden" )   
			oForm.elements[i].value="" ;	
	}
}
//*** Aceita somente numeros (0-9) e pontos o codigo estrutural
//    Por  : Régis Steigleder
//    Data : 20/12/2005 
function formataTeclaCodEstrutural(e)
{
	if (document.all) // Internet Explorer
		var tecla = event.keyCode;
	else if(document.layers) // Nestcape
		var tecla = e.which;
	if ((tecla > 47 && tecla < 58) || tecla == 46) // numeros de 0 a 9 e ponto (.)
		return true;
	else{
		if (tecla != 8 && tecla!= 13) // backspace
			event.keyCode = 0;
		else return true;
	}
}

//*** AUTOTAB
//	 	Pelo Length passado ou pressionando a tecla ENTER,
//		para usar a tabulacao somente com o ENTER, eh só passar com Length == "0";
//Ex1: onKeyUp="return autoTab(this, 3, event);"
// 		Pula os campos do formulario na sequencia
//Ex2: onKeyUp="return autoTab(this, 3, event, FT_DT_Encerramento);"
//		Pula para o campo desejado, independente de ordem no formulario
//-------------------------------------------------------------------------------------
//    Por  : André Valadas
//    Data : 29/12/2005
function autoTab(input, len, e, nextInput)
{
	var isNN = (navigator.appName.indexOf("Netscape")!=-1);
	var filter = (isNN) ? [0,8,9] : [0,8,9,16,17,18,37,38,39,40,46];
	var keyCode = (isNN) ? e.which : e.keyCode;
	if(keyCode == 13 || (len > 0 && input.value.length >= len && !containsElement(filter,keyCode)))
	{
		input.value = input.value.slice(0, len > 0 ? len : input.value.length);
		if (nextInput == null)
			input.form[(getIndex(input)+1) % input.form.length].focus();
		else nextInput.focus();
		return true;
	} else return false;
	function containsElement(arr, ele)
	{
		var found = false, index = 0;
		while(!found && index < arr.length)
			if(arr[index] == ele)
				found = true;
			else index++;
		return found;
	}
	function getIndex(input)
	{
		var index = -1, i = 0, found = false;
		while (i < input.form.length && index == -1)
			if (input.form[i] == input)
				index = i;
			else i++;
		return index;
	}
}
//  FIM AUTOTAB -->

/**
 * Insere zeros a esquerda
 * @param input campo(this)
 * @param int NewLen - tamanho da nova string com zeros a esquerda
 * @param fillChar caracter que sera usado para porencher o tamanho
 * Por : André Valadas
 * Data: 21/02/2006
 */
function LFill(input, NewLen, fillChar)
{
	var result = "";
    if (input.value != null)
    	result = input.value;
	if (result.length <= NewLen)
    {
    	while (result.length < NewLen)
        	result = fillChar + result;
	} else result = result.substring(0, NewLen);
    input.value = result;
}