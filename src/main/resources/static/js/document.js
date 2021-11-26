$('document').ready(function(){
	$('.deleteButton').on('click',function(event){
	event.preventDefault();
	var href = $(this).attr('href')
	$('#confirmDelete').attr('href',href);
	$('#delete').modal()
	});
	
	$('.remarkButton').on('click',function(event){
	event.preventDefault();
	var href = $(this).attr('href')
	$('#confirmRemark').attr('href',href);
	$('#remark').modal()
	});
	
	$('.unremarkButton').on('click',function(event){
	event.preventDefault();
	var href = $(this).attr('href')
	$('#unconfirmRemark').attr('href',href);
	$('#unremark').modal()
	});
	
	$('.shareButton').on('click',function(event){
	event.preventDefault();
	var href = $(this).attr('href')
	$('#share').attr('action',href);
	$('#shareModel').modal()
	});
});

