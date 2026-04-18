const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

const [N, M] = input[0].split(" ").map(Number);
const countryList = [];
const countryToIdx = {};
for (let i = 0; i < N; i++) {
  let country = input[1 + i];
  countryList.push(country);
  countryToIdx[country] = countryList.length - 1;
}

const suzerainList = [...Array(N)].map((_, i) => i);

for (let i = 0; i < M; i++) {
  let [c1, c2, result] = input[1 + N + i].split(",");

  // 이긴 국가를 c1으로 지정
  if (result === "2") [c1, c2] = [c2, c1];

  let [i1, i2] = [countryToIdx[c1], countryToIdx[c2]];
  // 종주국와 속국 대결인지 판단. 맞으면 내부에서 처리하고 넘어감
  if (isUprising(i1, i2)) continue;

  union(i1, i2);
}

let answer = [];
suzerainList.forEach((v, i) => {
  if (v === i) {
    answer.push(countryList[i]);
  }
});
answer.sort();
console.log(`${answer.length}\n${answer.join("\n")}`);

function isUprising(a, b) {
  if (a === find(b)) {
    return true;
  }
  if (find(a) === b) {
    // 속국이 이김
    suzerainList[a] = a;
    suzerainList[b] = a;
    return true;
  }
  return false;
}

// a가 b의 종주국이 됨.
function union(a, b) {
  a = find(a); // 이긴 국가가 속국일 수 있음
  b = find(b);
  suzerainList[b] = a;
}

function find(a) {
  if (suzerainList[a] === a) return a;

  let parent = find(suzerainList[a]);
  suzerainList[a] = parent;
  return parent;
}
