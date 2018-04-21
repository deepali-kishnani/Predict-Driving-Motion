library(rpart)
library(caret)
library(rpart.plot)
library(FSelector)
library(ROCR)

library(data.table) 

setwd("C:\\Users\\Lenovo\\Desktop\\IIIT-Delhi\\semester 7\\IP_LOGS")
sample70 <- read.csv("trainingdata2.csv", stringsAsFactors=F)
s <- sample70[1:2]
sample30<- read.csv("data-train.csv", stringsAsFactors=F)
tree <- rpart(target ~ ., method = "class" , data = s, parms = list(split = "information"))
summary(tree)

test <- sample30
test_target <- test$target
test$target <- NULL

prp(tree, main="Decision Tree")
decisionTree_predict <- predict(tree, test, type="class")
decisionTree_prob <- predict(tree, test, type="prob")
pred <- prediction(decisionTree_prob[,2], test_target)
perf = performance(pred, "tpr" , "fpr")
plot(perf)
dtree_confusionMatrix<- confusionMatrix(decisionTree_predict, test_target)
save(dtree_confusionMatrix, file = "ConfusionMatrix_DTree.Rdata")
