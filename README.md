# WebDataIntegration
Web Data Integration project

### Project abstract
See /latex/ProjectAbstract.pdf for the current project abstract. 


### DBPedia
#### Companies
Put queries under: [queries/company_dbpedia](/queries/company_dbpedia)

This [gist](https://gist.github.com/szydan/e801fa687587d9eb0f6a) seems like it might be a good starting point, also for batch downloading, in case not all results can be downloaded at the same time.

#### Locations
Put queries under: [queries/location_dbpedia](/queries/location_dbpedia)


### Freebase
Put queries under: [queries/company_freebase](/queries/company_freebase)


## FUSEKI/TBD
Probably won't need this, but could run a SPARQL server locally with this. Launch with 

```
fuseki-server --update --locl ../tbd/BNBLODB_sample //BNBLODB_sample
```

Then open web console at localhost:3030, can upload file there