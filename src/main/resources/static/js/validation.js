/**
 * 
 */

function soValid(formId) {
	let form = document.getElementById(formId);
	let valid = true;

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
				
				alert(msg);
				
				setTimeout(function() {
					element.focus();
					element.scrollIntoView({ behavior: 'smooth', block: 'center' });
				}, 0);
	
				valid = false;
				return false;
			}
			
			if (element.hasAttribute('data-valid-minimum') && (value.length < parseInt(element.getAttribute('data-valid-minimum')))) {
				let min = element.getAttribute('data-valid-minimum');
				msg = `${labelname} deve ter pelo menos ${min} caracteres`;
				
				alert(msg);
				
				setTimeout(function() {
					element.focus();
					element.scrollIntoView({ behavior: 'smooth', block: 'center' });
				}, 0);
				
				valid = false;
				return false;
			}
			
			if (element.hasAttribute('data-valid-maximum') && (value.length > parseInt(element.getAttribute('data-valid-maximum')))) {
				let max = element.getAttribute('data-valid-maximum');
				let msg = `${labelname} não pode exceder ${max} caracteres`;
				// por : A senha não pode exceder 10 caracteres
				// eng : Password cannot exceed 10 characters
				
				alert(msg);
				
				setTimeout(function() {
					element.focus();
					element.scrollIntoView({ behavior: 'smooth', block: 'center' });
				}, 0);
				
				valid = false;
				return false;
			}
			
		}
		
	});

	return valid;
}
