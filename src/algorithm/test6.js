function solution(x) {
  let strNum = x.toString();
  let sum = 0;
  strNum.split("").forEach((value) => sum += parseInt(value));
  return (x % sum === 0);
}

console.log(solution(11));
// console.log('A:', solution([100, 81, 99, 51, 22, 63, 34, 75, 41], [[2, 5, 3], [4, 4, 1], [1, 7, 3]]));

