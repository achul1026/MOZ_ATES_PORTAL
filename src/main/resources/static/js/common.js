//$(document).ready(function(){
//	
//	var langValue = $.cookie("lang");
//	if( langValue == "" || langValue == undefined ){
//		langValue = "eng";
//		$.cookie("lang",langValue, {expired: 365, path:"/"});
//	}else{
//		langValue = langValue.toLowerCase();
//	}
//	$("#langCd").val(langValue);
//	
//	$("#langCd").on("change",function(){
//		var pickLang = $("#langCd option:selected").val();
//		$.cookie("lang", pickLang, {expired: 365, path:"/"});
//		
//		window.location.replace("/");
//	})
	
 	
//	  $('.mobile-list-title').click(function(){
//	 // alert('asdasd')
//	/* $('.sub-list').slideToggle(500);*/
//	  if ($(this).hasClass("on")) {
//		  //alert('온');
//		    $(this).siblings().slideUp();
//            $(this).find('.arrow').removeClass('on') 
//            $(this).removeClass('on');
//        } else {
//			//alert('오프');
//			 $(this).siblings().slideDown();
//			 $(this).find('.arrow').addClass('on')
//			 $(this).addClass('on')
//			 $('.mobile-list-title').not(this).siblings().slideUp();
//			 $('.mobile-list-title').not(this).find('.arrow').removeClass('on');
//			 $('.mobile-list-title').not(this).removeClass('on');
//        }
//  });

//  new Swiper('.swiper', {
//	  autoplay: {
//	    delay: 5000,
//	  },
//	   slidesPerView: 4,
//	  spaceBetween: 10,
//	  pagination: {
//	    el: '.swiper-pagination',
//	    type: 'fraction'
//	  },
//	  breakpoints: {
//	    // 화면의 넓이가 640px 이상일 때
//	    640: {
//	      slidesPerView: 1,
//	      spaceBetween:0
//	    }
//	  }
//	});
//});

function mobileMenu(tg){
	let hamberger = document.getElementById(tg);
	 if(hamberger.style.display==='block'){
        hamberger.style.display='none';
    }else{
        hamberger.style.display='block';
    }
}

function fileUpload() {
	//file upload
      
        let $fileInput = $("#uploadFiles");
        let $fileList = $("#uploadListContainer");

        const inputFile = $fileInput[0].files;
        $fileInput.click();
        $fileInput.off().on("change", function() {
            if(!fileUploadChange(inputFile) || !fileUploadChange(this.files)){
                $fileInput.prop('files',inputFile);
                return;
            }
            if(inputFile.length > 0){
                fileChangeAddEvent(inputFile,this.files);
            } else{
                fileChangeDefaultEvent(this.files);
            }
            $('.uploadWrap').removeClass('none')
        });
     

        function fileChangeAddEvent(inputFileArr , addFileArr){
            const dataTransfer = new DataTransfer();
            Array.from(inputFileArr).forEach(file => {
                addUniqueFile(file, dataTransfer);
            });
            Array.from(addFileArr).forEach(file => {
                addUniqueFile(file, dataTransfer);
            });
            handleFiles(dataTransfer.files);
            $fileInput.prop('files',dataTransfer.files);
        }

        function fileChangeDefaultEvent(files){
            handleFiles(files);
            $fileInput.prop('files',files);
        }

        function addUniqueFile(file, dataTransfer) {
            const uniqueFileSet = new Set();
            const uniqueFiles = [];
            const uniqueKey = file.name + "_" + file.size;

            for (let i = dataTransfer.items.length - 1; i >= 0; i--) {
                const file = dataTransfer.items[i].getAsFile();
                if (uniqueFileSet.has(file.name + "_" + file.size)) {
                    dataTransfer.items.remove(i);
                } else {
                    uniqueFileSet.add(file.name + "_" + file.size);
                    uniqueFiles.push(file);
                }
            }
            if (!uniqueFileSet.has(uniqueKey)) {
                uniqueFileSet.add(uniqueKey);
                dataTransfer.items.add(file);
            }
        }

        function handleFiles(files) {
            if (files.length === 0) return;
            $("#uploadListContainer").empty();
            for (let i = 0; i < files.length; i++) {
                let fileNm = files[i].name;
                var uploadList = $(`
	                <div class="uploadList">
                		<div class="listItem">
                			<div class="fileList">
		                		`+fileNm+`
                			</div>
	                		<button type="button" data-value='`+fileNm+`' class="fileDelBtn">
									<img src="/images/upload_close.png" alt="업로드파일 삭제">
	                		</button>
                		</div>
	                </div>`);
                console.log($fileList);
                console.log(uploadList);
                $fileList.append(uploadList);
            }
			 
            $(".fileDelBtn").on('click',function(){
                var $this = $(this);
                var fileNm = $this.data('value');
                const dataTransfer = new DataTransfer();
                let trans = $('#uploadFiles')[0].files;
                let fileArray = Array.from(trans);
	
                $this.closest('.uploadList').remove();
                fileArray.filter(file => file.name != fileNm).forEach(file => {
                    dataTransfer.items.add(file);
                });
                $fileInput.prop('files',dataTransfer.files);
                
                let uploadLength = $('#uploadListContainer').children().length;
        		if(uploadLength <= 0){
        			$('.uploadWrap').addClass('none')
        		}
            });
        }


    function fileUploadChange(files){
        var maxBytes = 5242880;
        var fileNmMaxLength = 50;

        for(var i = 0; i < files.length; i++){
            var fileNm = files[i].name;
            var fileBytes = 0;

            if(fileNm != ''){
                fileBytes = files[i].size;
            }
            if(fileNm != ''){
                var ext = fileNm.slice(fileNm.lastIndexOf(".")+1).toLowerCase();
                //if(ext != 'png' && ext != 'jpg' && ext != 'jpeg' && ext != 'mp4'){
				//	alert('JPG, PNG, MP4 파일만 첨부 가능합니다.')
                //    return false;
                //}else 
                if(fileBytes > maxBytes){
                    return false;
                }else if(fileNm.length > fileNmMaxLength){
				//	new ModalBuilder().init().alertBoby('파일 제목은 50자 이상 넘을 수 없습니다.').footer(4,'확인',function(button, modal){modal.close();}).open();
				//	modalAlertWrap();      
                    return false;
                }
            }
        }
        return true;
    }
}
/* 이태유 추가 */
function numberComma(number) {
	if(number == null) return 0;
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}