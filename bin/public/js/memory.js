window.addEventListener("click", function(event) {
	if(event.target.className == "notclicked") {
		var buttonId = event.target.id;
		var row = buttonId.substring(1, 2);
		var col = buttonId.substring(2, 3);

		row = parseInt(row + 1);
		col = parseInt(col);

		var hidden = document.getElementById("hiddenvals");

		var firstdemstring = hidden.value.replace("[", "");
		firstdemstring = firstdemstring.replace(/\]/g,' ');
		firstdemstring = firstdemstring.replace(/\\/g,' ');
		firstdemstring = firstdemstring.replace(/\s+/g, '');

		var hulparray = new Array();
		var letterArray = new Array();
		hulparray = firstdemstring.split("[");

		for (var first in hulparray) {
			if(hulparray[first] !== "") {
				letterArray[first] = hulparray[first].split("");
			}
		}
		showNumber(buttonId, letterArray[row][col]);
	}

	function showNumber(buttonId, val) {
		var ele = document.getElementById(buttonId);
		ele.value = val;
		setTimeout("", 1500);
	}
});