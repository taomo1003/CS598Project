function showResult(input){
	if (input.length==0)  {
		$("#result").text("");
		return;
	}
	switch($("#sel2").val()) {
    case "Sum":
        $("#result").text("Sum from "+ getInputSummary(input) +sum);
        break;
    case "Average":
    	$("#result").text("Average from "+ getInputSummary(input) +avg);
        break;
    case "Median":
    	$("#result").text("Median from "+ getInputSummary(input) +median);
        break;
    case "Max":
    	$("#result").text("Max from "+ getInputSummary(input) +max);
        break;
    case "Min":
    	$("#result").text("Min from "+ getInputSummary(input) +min);
        break;
	}
}

function getInputSummary(input){
	var result = "row ";
	for (var i in input){
		result += (input[i][0] + "----" + input[i][1]+",");
	}
	return result.slice(0, -1)+" : ";
}

function getResult(input){
	var col  = 3;
	var values = [];
	for (var i in input){
		$.getJSON( "MockGetData?col="+col+"&start="+input[i][0]+"&end="+input[i][1]+"&skipFirst=T", function( data ) {
		      for (var i=0; i<data.length; i++){
			     data[i] = parseInt(data[i], 10);
			  }
			
			  values = values.concat(data);
			  sum = values.reduce((previous, current) => current += previous);
			  avg = parseFloat((sum / values.length).toFixed(2),10);
			  
			  //get median
			  values.sort((a, b) => a - b);
			  median = (values[(values.length - 1) >> 1] + values[values.length >> 1]) / 2
			  
			  //get max / min
			  max = Math.max.apply(null, values);
			  min = Math.min.apply(null, values);
			  
			  console.log(max);
			  console.log(min);
			  console.log(avg);
			  console.log(sum);
			  console.log(median);
			  showResult(input);
		});
	}
}
