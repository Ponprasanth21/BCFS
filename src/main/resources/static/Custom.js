var VirtualKeyboard = MindFusion.VirtualKeyboard;
var KeyboardState = MindFusion.KeyboardState;
var KeyboardLayout = MindFusion.KeyboardLayout;

document.addEventListener("DOMContentLoaded", function (event)
{
	var vk = VirtualKeyboard.create(
		document.getElementById("keyboard"));
    KeyboardState.NumLock = true;
	var layout = KeyboardLayout.create(numpadDef);
	vk.setLayout(layout);
});