
function soValid(formId) {
	let form = document.getElementById(formId);
	let valid = true;
	const pwRegEx = /^[a-zA-Z0-9!@#$%^&*]+$/;
	function checkTel(num) {
	    const regex = /^\d+$/;
		return regex.test(num);
	}

	function checkBrthDt(brthDt) {
		// dd-MM-yyyy
		const regex = /^(0[1-9]|[12]\d|3[01])(0[1-9]|1[0-2])\d{4}$/;
		return regex.test(brthDt.replace(/\D/g, ''));
	}

	let elements = form.querySelectorAll('.data-validate');
	elements.forEach(function(element) {
		if(valid) {
			let tagName = element.tagName.toUpperCase();
			let value = element.value || '';
			let attributeName = '';
			let labelname = element.getAttribute('data-valid-name');
			let msg = "Os dados que foram fornecidos são inválidos";
			// 제공된 데이터가 잘못되었습니다.
			
			if (element.hasAttribute('data-valid-required') && (value.trim() === '')) {
				msg = `Por favor, insira uma ${labelname}`;
				if (element.type.toUpperCase() === 'RADIO' || element.type.toUpperCase() === 'CHECKBOX') {
					msg = `Por favor, selecione uma ${labelname}`;
				}
				
				new ModalBuilder().init().alertBody(msg).footer(4, 'OK', function (button, modal) {
					modal.close();
					setTimeout(function() {
						element.focus();
						element.scrollIntoView({ behavior: 'smooth', block: 'center' });
					}, 0);
				}).open();
				
	
				valid = false;
				return false;
			}
			
			if (element.hasAttribute('data-valid-minimum') && (value.length < parseInt(element.getAttribute('data-valid-minimum')))) {
				let min = element.getAttribute('data-valid-minimum');
				msg = `${labelname} deve ter pelo menos ${min} caracteres`;
				
				new ModalBuilder().init().alertBody(msg).footer(4, 'OK', function (button, modal) {
					modal.close();
					setTimeout(function() {
						element.focus();
						element.scrollIntoView({ behavior: 'smooth', block: 'center' });
					}, 0);
				}).open();
				
				
				valid = false;
				return false;
			}
			
			if (element.hasAttribute('data-valid-maximum') && (value.length > parseInt(element.getAttribute('data-valid-maximum')))) {
				let max = element.getAttribute('data-valid-maximum');
				let msg = `${labelname} não pode exceder ${max} caracteres`;
				// por : A senha não pode exceder 10 caracteres
				// eng : Password cannot exceed 10 characters
				
				new ModalBuilder().init().alertBody(msg).footer(4, 'OK', function (button, modal) {
					modal.close();
					setTimeout(function() {
						element.focus();
						element.scrollIntoView({ behavior: 'smooth', block: 'center' });
					}, 0);
				}).open();
				
				
				valid = false;
				return false;
			}
			
			if (element.hasAttribute('data-valid-tel') && !checkTel(value)) {
				// 전화번호는 숫자만 입력할 수 있습니다.
				let msg = 'Os números de telefone só podem conter números.';
				
				new ModalBuilder().init().alertBody(msg).footer(4, 'OK', function (button, modal) {
					modal.close();
					setTimeout(function() {
						element.focus();
						element.scrollIntoView({ behavior: 'smooth', block: 'center' });
					}, 0);
				}).open();
				
				
				valid = false;
				return false;
			}
			
			if (element.hasAttribute('data-valid-brth') && !checkBrthDt(value)) {
				// 생년월일 형식이 잘못되었습니다.
				let msg = 'O formato da data de nascimento está incorreto.';
				
				new ModalBuilder().init().alertBody(msg).footer(4, 'OK', function (button, modal) {
					modal.close();
					setTimeout(function() {
						element.focus();
						element.scrollIntoView({ behavior: 'smooth', block: 'center' });
					}, 0);
				}).open();
				
				
				valid = false;
				return false;
			}

			if (element.hasAttribute('data-valid-pw') && !pwRegEx.test(value)) {
				// 비밀번호 형식이 잘못 되었습니다.
				let msg = 'O formato da senha está incorreto.';

				new ModalBuilder().init().alertBody(msg).footer(4, 'OK', function (button, modal) {
					modal.close();
					setTimeout(function() {
						element.focus();
						element.scrollIntoView({ behavior: 'smooth', block: 'center' });
					}, 0);
				}).open();


				valid = false;
				return false;
			}
			
		}
		
	});

	return valid;
}
