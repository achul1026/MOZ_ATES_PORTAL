
/**
	  node.empty();
 */
Node.prototype.empty = function() {
	this.innerHTML = "";
}

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

function mobileMenu() {
	const hamberger = document.querySelector('.mobile-menu-icon');
	const mobileDrop = document.getElementById('mobileMenu');
	if (hamberger.classList.contains('on')) {
		hamberger.classList.remove('on');
		mobileDrop.style.display = 'none';
	} else {
		hamberger.classList.add('on');
		mobileDrop.style.display = 'block';
	}
}

function fileUpload(maxFileSizeMB, maxFileCount, maxFileNameLength, allowedExtensions, oldFileLength = 0, oldFileNmArr = {}) {
	let $fileInput = $("#uploadFiles");
	let $fileList = $("#uploadListContainer");

	const inputFile = $fileInput[0].files;

	$fileInput.click();
	$fileInput.off().on("change", function() {
		// 파일 업로드 가능 개수 체크
		if (inputFile.length + this.files.length > (maxFileCount - oldFileLength)) {
			$fileInput.prop('files', inputFile);
			new ModalBuilder().init().alertBody(`Não pode haver mais de ${maxFileCount} arquivos.`).footer(4, 'OK', function(button, modal) {
				// 파일은 최대 ${maxFileCount}개를 넘을 수 없습니다.
				modal.close();
			}).open();
			return;
		}
		// 확장자, 용량, 이름 길이 체크
		if (!fileUploadChange(inputFile) || !fileUploadChange(this.files)) {
			$fileInput.prop('files', inputFile);
			return;
		}
		if (inputFile.length > 0) {
			fileChangeAddEvent(inputFile, this.files);
		} else {
			fileChangeDefaultEvent(this.files);
		}
		$('.uploadWrap').removeClass('none')
	});


	function fileChangeAddEvent(inputFileArr, addFileArr) {
		const dataTransfer = new DataTransfer();
		Array.from(inputFileArr).forEach(file => {
			addUniqueFile(file, dataTransfer);
		});
		Array.from(addFileArr).forEach(file => {
			addUniqueFile(file, dataTransfer);
		});
		handleFiles(dataTransfer.files);
		$fileInput.prop('files', dataTransfer.files);
	}

	function fileChangeDefaultEvent(files) {
		handleFiles(files);
		$fileInput.prop('files', files);
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
                		<div class="listItem modify">
                			<div class="fileList">
		                		`+ fileNm + `
                			</div>
	                		<button type="button" data-value='`+ fileNm + `' class="fileDelBtn">
									<img src="/images/upload_close.png" alt="업로드파일 삭제">
	                		</button>
                		</div>
	                </div>`);
			$fileList.append(uploadList);
		}

		$(".fileDelBtn").on('click', function() {
			var $this = $(this);
			var fileNm = $this.data('value');
			const dataTransfer = new DataTransfer();
			let trans = $('#uploadFiles')[0].files;
			let fileArray = Array.from(trans);

			$this.closest('.uploadList').remove();
			fileArray.filter(file => file.name != fileNm).forEach(file => {
				dataTransfer.items.add(file);
			});
			$fileInput.prop('files', dataTransfer.files);

			let uploadLength = $('#uploadListContainer').children().length;
			if (uploadLength <= 0 && oldFileLength < 1) {
				$('.uploadWrap').addClass('none')
			}
		});
	}

	function fileUploadChange(files) {
		var maxBytes = maxFileSizeMB * 1024 * 1024;

		for (var i = 0; i < files.length; i++) {
			var fileNm = files[i].name;
			var fileBytes = 0;

			if (fileNm != '' && oldFileNmArr.length > 0) {
				let isFileDuplicated = false;
				oldFileNmArr.forEach(oldFileNm => {
					if (oldFileNm.toUpperCase() == fileNm.toUpperCase()) {
						isFileDuplicated = true;
						return false;
					}
				});
				if (isFileDuplicated) {
					new ModalBuilder().init().alertBody('Existem arquivos duplicados.').footer(4, 'OK', function(button, modal) {
						// 중복된 파일이 존재합니다.
						modal.close();
					}).open();
					return false;
				}
			}

			if (fileNm != '') {
				fileBytes = files[i].size;
			}
			if (fileNm != '') {
				let fileReg = new RegExp("(.*?)\\.(" + allowedExtensions.join("|") + ")$");
				// fileReg = /(.*?)\.(jpg|jpeg|png)$/
				if (!fileReg.test(fileNm)) {
					new ModalBuilder().init().alertBody(`Por favor, verifique a extensão do arquivo.(${allowedExtensions.join(", ")})`)
						.footer(4, 'Confirmar', function(button, modal) {
							// 파일 확장자를 확인 해 주세요.
							modal.close();
						}).open();
					return false;
				}
				if (fileBytes > maxBytes) {
					new ModalBuilder().init().alertBody(`O tamanho do arquivo não pode exceder ${maxFileSizeMB}MB.`)
						.footer(4, 'Confirmar', function(button, modal) {
							// 파일 용량은 ${maxFileSizeMB}MB를 초과할 수 없습니다.
							modal.close();
						}).open();
					return false;
				}
				if (fileNm.length > maxFileNameLength) {
					new ModalBuilder().init().alertBody(`Os títulos dos arquivos não podem exceder ${maxFileNameLength} caracteres.`)
						.footer(4, 'Confirmar', function(button, modal) {
							// 파일 제목은 ${maxFileNameLength}자 이상을 넘을 수 없습니다.
							modal.close();
						}).open();
					return false;
				}
			}
		}
		return true;
	}
}

/**
 * 생년월일 유효성 keyup 이벤트
 */
function keyupDateCheck(event, pattern, separator) {
	// 패턴 및 구분자 유효성 검사
	if (!["yyyyMMdd", "ddMMyyyy", "MMddyyyy"].includes(pattern)) {
		console.warn("keyupDateCheck() --> Invalid Date Pattern");
		event.target.value = '';
		return false;
	}
	if (separator.length > 1) {
		console.warn("keyupDateCheck() --> Separator Length Too Long");
		event.target.value = '';
		return false;
	}

	const inputKey = event.key;
	if (inputKey == "Backspace" || inputKey == "Delete") {
		event.target.value = '';
		return false;
	}

	// 숫자 외 문자 제거
	let dateVal = event.target.value.replace(/\D/g, "");

	// MM과 dd값에 따라 0삽입
	switch (pattern) {
		case "yyyyMMdd":
			// day
			if (dateVal.length == 7 && dateVal.charAt(6) > 3) {
				dateVal = dateVal.slice(0, 6) + 0 + dateVal.slice(6);
			}
			// Month
			if (dateVal.length == 5 && dateVal.charAt(4) > 1) {
				dateVal = dateVal.slice(0, 4) + 0 + dateVal.slice(4);
			}
			break;
		case "ddMMyyyy":
			// day
			if (dateVal.length == 1 && dateVal > 3) {
				dateVal = 0 + dateVal;
			}
			// Month
			if (dateVal.length == 3 && dateVal.charAt(2) > 1) {
				dateVal = dateVal.slice(0, 2) + 0 + dateVal.slice(2);
			}
			break;
		case "MMddyyyy":
			// day
			if (dateVal.length == 3 && dateVal.charAt(2) > 3) {
				dateVal = dateVal.slice(0, 2) + 0 + dateVal.slice(2);
			}
			// Month
			if (dateVal.length == 1 && dateVal > 1) {
				dateVal = 0 + dateVal;
			}
			break;
	}

	// 패턴에 따른 날짜 유효성 검사
	const dayRegex = /^0[1-9]|[12]\d|3[01]$/;
	const monthRegex = /^0[1-9]|1[0-2]$/;
	const today = new Date();
	const yearToday = today.getFullYear();
	let year = "", month = "", day = "";

	let dayValidNum = 0, monthValidNum = 0, yearValidNum = 0;
	if (pattern == "ddMMyyyy") {
		dayValidNum = 6;
		monthValidNum = 2;
		yearValidNum = 4;
	} else if (pattern == "MMddyyyy") {
		dayValidNum = 4;
		monthValidNum = 4;
		yearValidNum = 4;
	}

	// day
	if (dateVal.length >= 8 - dayValidNum) {
		day = dateVal.slice(6 - dayValidNum, 8 - dayValidNum);
		if (!dayRegex.test(day)) {
			event.target.value = '';
			return false;
		}
	}

	// Month
	if (dateVal.length >= 6 - monthValidNum) {
		month = dateVal.slice(4 - monthValidNum, 6 - monthValidNum);
		if (!monthRegex.test(month)) {
			event.target.value = '';
			return false;
		}
		month -= 1; // month starts from 0
	}

	// year
	if (dateVal.length >= 4 + yearValidNum) {
		year = dateVal.slice(0 + yearValidNum, 4 + yearValidNum);
		if (year > yearToday) {
			event.target.value = '';
			return false;
		}
	}

	// 패턴에 따른 날짜 형식화
	let valLength = 0;
	let sliceIdx = 0;
	if (pattern == "yyyyMMdd") {
		valLength = 3;
		sliceIdx = 2;
	}

	if (dateVal.length > 1 + valLength) {
		dateVal = dateVal.slice(0, 2 + sliceIdx) + separator + dateVal.slice(2 + sliceIdx);
	}

	if (dateVal.length > 4 + valLength) {
		dateVal = dateVal.slice(0, 5 + sliceIdx) + separator + dateVal.slice(5 + sliceIdx);
	}

	// 날짜 유효성 검사

	if (dateVal.length > 9) {
		let finalRegex = "";
		switch (pattern) {
			case "yyyyMMdd": finalRegex = /^\d{4}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])$/; break;
			case "ddMMyyyy": finalRegex = /^(0[1-9]|[12]\d|3[01])(0[1-9]|1[0-2])\d{4}$/; break;
			case "MMddyyyy": finalRegex = /^(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{4}$/; break;
		}
		const dateCheck = new Date(year, month, day);

		if (dateCheck.getMonth() != month
			|| dateCheck.getDate() != day
			|| dateCheck.getFullYear() != year
			|| !(dateCheck < today)
			|| !finalRegex.test(dateVal.replace(/\D/g, ""))
			|| dateCheck === "Invalid Date") {
			event.target.value = '';
			return false;
		}
	}

	event.target.value = dateVal;
}

/**
 * Call Loading layer
 */
class PortalLoading {
	constructor() {
		let loading_cover = null;

	}

	start = function(text = "loading") {
		const loading_html = `<div id="loading-cover">
	    						 <div id="loadingContainer">
	    						 	 <div class="scaling-dots"><div></div><div></div><div></div></div>
	               					 <p id="loadingText">${text}</p>
	    						 </div>
	       					  </div>`;
		this.loading_cover = document.createElement('div');
		this.loading_cover.innerHTML = loading_html;
		document.body.appendChild(this.loading_cover);

		return this;
	}

	end = function() {
		this.loading_cover.remove();
		this.loading_cover = null;
		return this;
	}
}

function retainNumbers(_this) {
	_this.value = _this.value.replace(/\D/g, '');
}
function trimValues(inputArr) {
	let valid = true;
	inputArr.some(input => {
		let elmnt = document.getElementById(input);
		if (elmnt == null) {
			console.warn(`trimValues() -> Wrong id: ${input}`);
			valid = false;
			return true;
		}
		elmnt.value = elmnt.value.trim();
	})
	return valid;
}
function mobileDrop() {
    const mobileBtn = event.target;
    //const mobileDropTitle = mobileBtn.querySelector('.mobileMenuTitleWrap');
    const mobileDropList = mobileBtn.closest('.mobileDropList'); 
    const mobileDropSubList = mobileDropList.querySelector('.mobile-nav-sub-list');
    
    const isOpen = mobileBtn.classList.contains('on');
    const parentTarget = mobileBtn.closest('.mobileMenuTitleWrap');
    const nextTarget = mobileBtn.nextElementSibling;
    
    const mobileWrap = document.getElementById('mobileMenu');
    const mobileSubWrap = mobileWrap.querySelectorAll('.on');
	mobileSubWrap.forEach(el => el.classList.remove('on'));
   
    if (!isOpen) {
        mobileBtn.classList.add('on');
        mobileDropSubList.classList.add('on');
        parentTarget.classList.add('on');
        nextTarget.classList.add('on');
        mobileBtn.querySelector('.mobile-title').classList.add('on');
        mobileBtn.querySelector('.mobile-arrow').classList.add('on');
    }
}