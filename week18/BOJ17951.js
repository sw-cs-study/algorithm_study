/**
 * 장범준
 * 각 그룹의 최솟값을 정하고 이를 토대로 그룹을 나눠서 그룹 수가 k이상인 최솟값 중에 제일 큰값 구하기 => t t t t t f f f
 * 시간복잡도 nlog(n)
 */
const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, k] = input[0].split(" ").map(Number);
const arr = input[1].split(" ").map(Number);

let [l, r] = [1, 20 * n + 1];
while (l + 1 < r) {
  let midPoint = Math.floor((l + r) / 2);
  // 그룹 수 구하기
  let group = getGroups(arr, midPoint);
  if (group >= k) {
    l = midPoint;
  } else {
    r = midPoint;
  }
}
console.log(l);

function getGroups(arr, point) {
  let cnt = 0;
  let sum = 0;
  for (let p of arr) {
    sum += p;
    if (sum >= point) {
      cnt++;
      sum = 0;
    }
  }
  return cnt;
}
