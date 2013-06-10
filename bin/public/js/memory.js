window.addEventListener("click", function(event) {
	if(event.target.className == "notclicked") {
		var buttonId = event.target.id;
		var row = buttonId.substring(1, 2);
		var col = buttonId.substring(2, 3);
		
		row = parseInt(row);
		col = parseInt(col);
		
		
		var hidden = document.getElementById("hiddenvals");
		var firstdemstring = hidden.value.replace("[", "");
		firstdemstring = firstdemstring.replace(/\]/g,' ')
		firstdemstring = firstdemstring.replace(/\\/g,' ')
		var hulparray = new Array();
		hulparray = firstdemstring.split("[");
		//var chars = firstdemstring.split("\\");
		var chararray = new Array();
		var nieuwehulparray = new Array();
		var array = new Array();
		for (var first in hulparray) {
			if(hulparray[first] !== "") {
				//alert(hulparray[first]);
				nieuwehulparray[first] = hulparray[first].split(" ");
				for(var second in nieuwehulparray) {
					if(nieuwehulparray[second] !== "") {
						array[first] = nieuwehulparray[second];
					}
					
					
				}
			}
		}
		//showNumber(buttonId, array[row][col]);
	}
	
	function showNumber(buttonId, val) {
		var ele = document.getElementById(buttonId);
		ele.value = val;
		setTimeout(1000000000);
	}
});