basePath <- "C:\\Users\\D059373\\workspace java local\\webDataIntegration\\data\\resolutionResults\\"
file <- "companyForbes_2_companyFreebase_correspondences.csv"
path <- paste0(basePath, file)

data <- read.csv(path, header=F)
dataCorrect <- data
