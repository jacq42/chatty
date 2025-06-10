# Databricks

Source: [Databricks Course](https://www.databricks.com/learn/training/getting-started-with-data-engineering)

- Apache Spark + Delta Lake + MlFlow = Databricks Lakehouse Platform


> Databricks ist eine cloudbasierte Datenplattform, die ursprünglich von den Entwicklern von Apache Spark 
> gegründet wurde, um die Nutzung und Verwaltung großer Datenmengen zu vereinfachen. Die Plattform kombiniert 
> verschiedene Open-Source-Technologien wie Apache Spark, Delta Lake und MLflow und ermöglicht es Unternehmen, 
> eine sogenannte Lakehouse-Architektur aufzubauen. Diese vereint die Vorteile von Data Lakes 
> (flexible Speicherung großer, unstrukturierter Daten) und Data Warehouses (strukturierte Abfragen und Analyse) 
> in einer einzigen Plattform.
> 
> Databricks wird vor allem von Unternehmen genutzt, die große Datenmengen effizient verarbeiten, analysieren 
> und für KI-Anwendungen nutzen möchten. Die Plattform bietet eine zentrale, sichere und skalierbare Umgebung 
> für Data Engineering, SQL-Analysen und Machine Learning, wodurch Teams effizienter und produktiver 
> zusammenarbeiten können.
 
- Anwendungen:
  - **Big Data Processing**: Verarbeitung und Analyse riesiger Datenmengen, die auf mehrere Computer verteilt werden können, um hohe Rechenleistung zu gewährleisten.
  - **Data Engineering**: Automatisierung und Optimierung von ETL-Prozessen (Extract, Transform, Load), um Daten aus verschiedenen Quellen zu sammeln, zu bereinigen und für Analysen vorzubereiten.
  - **SQL-Analysen**: Bereitstellung eines serverlosen Data Warehouses für SQL-Abfragen und Business Intelligence (BI).
  - **Machine Learning**: Unterstützung des gesamten Lebenszyklus von KI- und Machine-Learning-Projekten – von der Datenaufbereitung über Experimente bis zur Produktion und Überwachung von Modellen.
  - **Kollaboration**: Notebook-Umgebungen ermöglichen es Datenwissenschaftlern, Data Engineers und Analysten, gemeinsam und interaktiv an Projekten zu arbeiten.
  - **Integration**: Einfache Anbindung an verschiedene Cloud-Anbieter wie Microsoft Azure, Amazon AWS und Google Cloud sowie an zahlreiche Datenquellen und BI-Tools. 

## Data Storage and Delta Lake

- Cloud storage system: own data
- Control plane. Databricks own cloud account
- encrypted transfer between data plane and control plane
- encrypted data at rest on control plane
- different roles can use the platform

### Data format

- Delta Lake = open-source project that enables building a data lakehouse on top of existing cloud storage
- ACID:
  - Atomicity: all transactions either succeed or fail
  - Consistency: 
  - Isolation: 
  - Durability: committed changes are permanent 
  - 
### Unity Catalog

- centralized metadata and user management
- centralized data access control
- data access auditing
- data linage
- data search and discovery
- secure data sharing with Delta Sharing

- Metastore:
  - top logic container
  - organizing your data and metadata
- Catalog:
  - top most container for data objects
  - first part of three level namespace
  - Three-level namespace:
    - SELECT * FROM catalog.schema.table
  - contains schema
    - Table (managed + external)
    - View
    - User defined Functions

### Data Management

- Medallion architecture:
  - Bronze: raw data
  - Silver: cleaned or enriched data
  - Gold: aggregated data for reporting and analysis

## Workflow Jobs

## Repos

- Notebooks can be stored in GitHub, Azure DevOps, GitLab, Bitbucket repositories

## Compute Resources

### Clusters

- collection of VM instances
- All-purpose clusters
  - analyze data collaboratively using interactive notebooks
- Job clusters
  -  run automated jobs
- cluster mode
  - single node
  - standard (multi node)
- databricks runtime version
  - standard
  - photon
  - machine learning: tensorFlow, Keras, PyTorch
- Access Mode
  - Single user
  - Shared
  - No isolation shared
  - Custom
- Cluster Policies
  - standardize cluster configuration
  - predefined configuration
  - simplify the user experience
  - cost control
  - enforce correct tagging
  - only admin users can edit cluster policies

## Databricks SQL