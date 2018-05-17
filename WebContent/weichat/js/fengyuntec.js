function changeHeight(obj){
	if(parseInt(obj.scrollHeight)>=parseInt(obj.style.height)){
		obj.style.height = obj.scrollHeight + 'px';
	}
	
}