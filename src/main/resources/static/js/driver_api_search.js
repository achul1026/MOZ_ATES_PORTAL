class DriverSelectAPI {
	constructor() {
		
    }
    
    async driverSearchData(dvrLcenId, callback) {
      let apiUrl = '/api/ites/searchDvrLcenId';
      let data = { 
			"searchType" : 'dvrLcenId',
			"searchValue" : dvrLcenId
			};
			
        fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => callback(data))
        .catch(error => 
        	callback(null)
        );
    }
}