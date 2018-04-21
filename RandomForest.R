library(randomForest)
library(ROCR)
library(caret)

library(data.table) 

setwd("C:\\Users\\Lenovo\\Desktop\\IIIT-Delhi\\semester 7\\IP_LOGS")
sample70 <- read.csv("data2.csv", stringsAsFactors=F)
#s <- sample70[2:3]
sample30<- read.csv("data-train.csv", stringsAsFactors=F)

y<-factor()
train<-sample70[1:2]
fit <- randomForest(as.factor(target)~.,data=train, ntree=500)
test<-sample30[2:2]

rforest_predict <- predict(fit, test)
rforest_predict_prob<-predict(fit,test,type = "prob")
pred <- prediction(rforest_predict_prob[,2],sample30$target)
perf <- performance(pred,"tpr","fpr")
plot(perf)

rforest_confMatrix<-confusionMatrix(data = rforest_predict,reference = sample30$target)