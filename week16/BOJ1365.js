/**
 * 꼬인 전깃줄
 * (알고리즘 봄) -> 요즘 문제 자체를 그대로 구현한다고 알고리즘을 못찾는거 같음. 그러지말고 정확한 목적을 찾아서(변형해서) 해당 목적에 맞는 알고리즘을 구현하자
 * 꼬이지 않으려면 꾸준히 증가하는 수열의 길이를 구하면 됨. 인덱스를 증가하면서 구하기 때문에 감소하는 수열은 꼬인 전깃줄이 됨.
 * 정확한 수열이 아닌 증가하는 수열의 "길이"를 구하는 것임
 * 그래서 오른쪽 값을 수열에 오름차순으로 저장하고 끝값보다 작으면 수열에 오른쪽 값의 lower_bound를 찾아서 넣음
 * 시간 복잡도: O(nlog(n))
 */

const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
const arr = input[1].split(" ").map(Number);
const lis = [];

for (let v of arr) {
  if (lis.length === 0) {
    lis.push(v);
    continue;
  }
  if (v > lis[lis.length - 1]) lis.push(v);
  else insert(lis, v);
}
console.log(n - lis.length);

function insert(lis, v) {
  let [l, r] = [-1, lis.length - 1];
  while (l + 1 < r) {
    let mid = Math.floor((l + r) / 2);
    if (lis[mid] >= v) {
      r = mid;
    } else {
      l = mid;
    }
  }
  lis[r] = v;
  return;
}
