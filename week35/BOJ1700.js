const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [n, k] = input[0].split(" ").map(Number);
const use = input[1].split(" ").map(Number);
const nextIdx = [...Array(k + 1)].map(() => []);
const curState = [Array(k + 1).fill(false)];
let filledCnt = 0;
for (let i = 0; i < k; i++) {
  let number = use[i];
  nextIdx[number].push(i);
}
for (let i = 1; i < k + 1; i++) {
  nextIdx[i].push(200);
}
let answer = 0;
for (let i = 0; i < k; i++) {
  let number = use[i];
  if (curState[number]) {
    let pop = nextIdx[number].shift();
    if (i !== pop) console.log("shit!!");
    continue;
  }
  if (filledCnt >= n) {
    answer++;
    let numberToBeChanged = -1;
    let idx = -1;
    // 현재 꼽힌거 중에 제일 나중에 다시 나오는 거 선택
    for (let j = 0; j < k + 1; j++) {
      if (!curState[j]) continue;
      if (idx < nextIdx[j][0]) {
        numberToBeChanged = j;
        idx = nextIdx[j][0];
      }
    }
    curState[numberToBeChanged] = false;
    filledCnt--;
  }
  filledCnt++;
  curState[number] = true;
  let pop = nextIdx[number].shift();
  if (i !== pop) console.log("shit!!");
}
console.log(answer);
