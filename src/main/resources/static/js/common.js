
$(document).ready(function(){
	
	var langValue = $.cookie("lang");
	if( langValue == "" || langValue == undefined ){
		langValue = "eng";
		$.cookie("lang",langValue, {expired: 365, path:"/"});
	}else{
		langValue = langValue.toLowerCase();
	}
	$("#langCd").val(langValue);
	
	$("#langCd").on("change",function(){
		var pickLang = $("#langCd option:selected").val();
		$.cookie("lang", pickLang, {expired: 365, path:"/"});
		
		window.location.replace("/");
	})
	
	/*pc 헤더 드롭메뉴*/
	$('.header').hover(function(){
		$('.drop-list').show();
	})
	$('.header').mouseleave(function(){
		$('.drop-list').hide();
	})
	
	$('.hamburger').click(function(){
		$('.mobile-nav').show();
	})
	$('.close').click(function(){
		$('.mobile-nav').hide();
	})
	
	  $('.mobile-list-title').click(function(){
	 // alert('asdasd')
	/* $('.sub-list').slideToggle(500);*/
	  if ($(this).hasClass("on")) {
		  //alert('온');
		    $(this).siblings().slideUp();
            $(this).find('.arrow').removeClass('on') 
            $(this).removeClass('on');
        } else {
			//alert('오프');
			 $(this).siblings().slideDown();
			 $(this).find('.arrow').addClass('on')
			 $(this).addClass('on')
			 $('.mobile-list-title').not(this).siblings().slideUp();
			 $('.mobile-list-title').not(this).find('.arrow').removeClass('on');
			 $('.mobile-list-title').not(this).removeClass('on');
        }
  });
  
  $("#file").on('change',function(){
  var fileName = $("#file").val();
  $(".upload-name").val(fileName);
});

});