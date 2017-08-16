function success(data) {
  console.log(data);
}

function error(error) {
  console.log(error);
}

rfr900.connect(succes, error);

rfr900.regReadCallback(success, error);
