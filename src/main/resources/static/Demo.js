var VirtualKeyboard = MindFusion.VirtualKeyboard;

document.addEventListener("DOMContentLoaded", function (event)
{
	var vk = VirtualKeyboard.create(
		document.getElementById("keyboard"));
	vk.setScaleToFitParent(false);

	f1.mode.onchange = function (event)
	{
		vk.setMode(Number(event.target.value));
	}
	f1.lang.onchange = function (event)
	{
		vk.setInputLocale(event.target.value);
	}
});