
library("caret")
library("klaR")
library("e1071")
library("ROCR")


library(data.table) 

setwd("C:\\Users\\Lenovo\\Desktop\\IIIT-Delhi\\semester 7\\IP_LOGS")
accel <- read.csv("data-training3.csv", stringsAsFactors=F)

xTrain <- accel[1:2]
nb_model <- naiveBayes(as.factor(target) ~ ., data = accel, laplace = 3)


Sample30<- read.csv("data-train.csv", stringsAsFactors=F)
xTest<-Sample30[2:2]

nb_predict <- predict(nb_model, xTest)
nb_predict_raw <- predict(nb_model, xTest,type="raw")
nb_predict_prob <- nb_predict_raw[,2]


pred <- prediction(nb_predict_prob,Sample30$target)
perf <- performance(pred,"tpr","fpr")
plot(perf)
nb_confusionMatrix<-confusionMatrix(data = nb_predict, reference =Sample30$target )
