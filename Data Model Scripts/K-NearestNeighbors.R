library(class)
library(ROCR)
library(caret)


library(data.table) 

setwd("C:\\Users\\Lenovo\\Desktop\\IIIT-Delhi\\semester 7\\IP_LOGS")
sample70 <- read.csv("trainingdata2.csv", stringsAsFactors=F)
sample30<- read.csv("data-train.csv", stringsAsFactors=F)

knnTrain<-sample70[1:1]
knnTest<-sample30[2:2]
knnClass<-sample70$target
knn_model<-knn(knnTrain, knnTest, knnClass, k = 21, prob = TRUE, use.all = TRUE)

knn_predict_prob<-attr(knn_model,"prob")
knn_predict_prob[knn_model==0] <- 1-knn_predict_prob[knn_model==0] 

knn_confMatrix<-confusionMatrix(data = knn_model,reference = sample30$target)

pred <- prediction(knn_predict_prob,sample30$target)

perf <- performance(pred,"tpr","fpr")
plot(perf)
