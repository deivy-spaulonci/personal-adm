function dataStringToDate(dateUS){
    var arrData = dateUS.split('-');
    return arrData[2] + '/' + arrData[1] + '/' + arrData[0];
}
function numberStringToMoeda(valor){
    return valor.toLocaleString('pt-br', {minimumFractionDigits: 2});
}