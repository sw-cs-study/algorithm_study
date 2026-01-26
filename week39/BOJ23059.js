const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const n = Number(input[0]);
// 각 아이템 별 진입 차수를 저장 + 먼저 사야하는 아이템을 키로 가지고 뒤에 오는 아이템을 배열값에 저장
const nextItems = {};
const itemIndegrees = {};
for (let i = 0; i < n; i++) {
  let [firstItem, secondItem] = input[1 + i].split(" ");
  if (nextItems[firstItem] === undefined) {
    nextItems[firstItem] = [secondItem];
  } else {
    nextItems[firstItem].push(secondItem);
  }

  if (itemIndegrees[firstItem] === undefined) {
    itemIndegrees[firstItem] = 0;
  }

  if (itemIndegrees[secondItem] === undefined) {
    itemIndegrees[secondItem] = 1;
  } else {
    itemIndegrees[secondItem]++;
  }
}
const itemCount = Object.keys(itemIndegrees).length;

// 진입차수가 0인 아이템 모아서 시작
let queue = [];
for (let item of Object.keys(itemIndegrees)) {
  if (itemIndegrees[item] === 0) {
    queue.push(item);
  }
}
queue.sort();

let answer = [];
while (queue.length > 0) {
  let nextQueue = [];
  for (let curItem of queue) {
    answer.push(curItem);
    if (nextItems[curItem] === undefined) continue;
    for (let item of nextItems[curItem]) {
      itemIndegrees[item]--;
      if (itemIndegrees[item] === 0) {
        nextQueue.push(item);
      }
    }
  }

  nextQueue.sort();
  queue = nextQueue;
}
console.log(answer.length === itemCount ? answer.join("\n") : -1);
