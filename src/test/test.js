'use strict';

function solution(v) {
  const answer = [];
  for (let i = 0; i < 2; i++) {
    const temp = [];
    for (let j = 0; j < v.length; j++) {
      temp.push(v[j][i]);
    }
    console.log(temp);
    answer.push(findOdd(temp))
  }
  console.log("answer :", answer);
  return answer;
}


function findOdd(Arr) {
//숫자를 하나씩 서치해서 개수 세고 개수가 홀수면 return하자..
  return Arr.reduce(function (acc, curr, i, arr) {

    console.log('acc: %s, curr: %s, i: %s, arr: %s', acc, curr, i, arr);
    let count = 0;
    let index = Arr.indexOf(curr);

    console.log('index: %s', index);

    while (index > -1) {
      count++
      index = Arr.indexOf(curr, index + 1)
    }

    if (count % 2 !== 0) {
      return curr
    }

    return acc
  }, Arr[0])
}

solution([[1, 4], [3, 4], [3, 10]])

