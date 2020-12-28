POST: http://localhost:8080/engine-rest/process-definition/key/{processId}/start
Body: []

POST: http://localhost:8080/engine-rest/task/{userTaskId}/complete
Body:
```javascript  
  Antrag überprüfen:
  {
    "variables": {
      "antragOk": {"value":true,"type":"Boolean"},
      "nachname": {"value":"Franz","type":"String"},
      "vorname": {"value":"Peters","type":"String"},
      "geburtsdatum": {"value":"2016-01-25T13:33:42.165+0100","type":"Date"},
      "lebensversicherungsprodukt": {"value":"Risiko","type":"String"},
      "versicherungssumme": {"value":45000,"type":"Long"}
    }
  }
  
  initiale Risikobewertung:
  {
  "variables": {
    "risikobewertung": {"value":7000,"type":"Long"}
  }
}

Angebot erstellen:
{
  "variables": {
    "angebot": {"value":"Text","type":"String"}
  }
}
````
