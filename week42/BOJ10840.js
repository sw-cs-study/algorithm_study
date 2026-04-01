const input = require("fs")
  .readFileSync(process.platform === "linux" ? "/dev/stdin" : "../sample.txt")
  .toString()
  .trim()
  .split("\n");

let A = input[0].trim();
let B = input[1].trim();

// A가 더 작은거
if (A.length > B.length) {
  [A, B] = [B, A];
}
const N = A.length;
const M = B.length;

const hashExpo = Array(26).fill(0);
let mul = 1n;
let base = 1501n;
for (let i = 0; i < 26; i++) {
  hashExpo[i] = mul;
  mul *= base;
}

let answer = 0;
for (let k = 1; k < N + 1; k++) {
  if (checkPossible(k)) answer = k;
}
console.log(answer);

// 파라미터로 들어온 길이의 구간 중에 알파벳 성분이 같은 구간이 있는지 체크
function checkPossible(k) {
  const hashSet = new Set();

  const elementsOfA = Array(26).fill(0);
  let aHash = 0n;
  // A 신호에서 i부터 k길이의 구간 성분 가져오기
  for (let i = 0; i < N - k + 1; i++) {
    if (i === 0) {
      // 구간 처음 뽑을 때
      aHash = 0n;
      for (let j = i; j < k; j++) {
        aHash += hashExpo[aToN(A, j)];
        elementsOfA[aToN(A, j)]++;
      }
    } else {
      // 첫번째 구간 아니면 해쉬 업데이트
      aHash -= hashExpo[aToN(A, i - 1)];
      aHash += hashExpo[aToN(A, i + k - 1)];
    }
    // 해싱
    hashSet.add(aHash);
  }

  const elementsOfB = Array(26).fill(0);
  let bHash = 0n;
  // B에서 k길이의 구간 뽑기
  for (let i = 0; i < M - k + 1; i++) {
    if (i === 0) {
      // 구간 처음 뽑을 때
      bHash = 0n;
      for (let j = i; j < k; j++) {
        bHash += hashExpo[aToN(B, j)];
      }
    } else {
      // 첫번째 구간 아니면 성분 배열 업데이트
      bHash -= hashExpo[aToN(B, i - 1)];
      bHash += hashExpo[aToN(B, i + k - 1)];
    }

    // A, B 구간 비교
    if (hashSet.has(bHash)) return true;
  }

  return false;
}

function aToN(str, i) {
  return str.charCodeAt(i) - "a".charCodeAt();
}
