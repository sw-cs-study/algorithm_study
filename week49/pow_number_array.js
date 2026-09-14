// 제곱 개수 배열의 길이는 최대 N^2. 따라서,
// 1. K 값을 구하는 시간복잡도 : O(N^2) -> 연속한 구간의 합을 구하는 거라 누적합을 쓰면 O(1) 가능, 배열을 만드는 데 O(N^2)이라 줄여야함.
// 2. C를 구하는 시간 복잡도 : O(N^4) -> 줄여야함

// 1번 줄이기
// 제곱 개수 배열을 일일이 만들지 않기. 그러면서 누적합 저장하기
// 예) [3, 6] -> [[2, 6], [8, 24]] : 2번 인덱스까지 합이 6, 8번 인덱스까지 합이 24, 여기서 각 [2, 6] 이런 애들을 '묶음'이라고 부르기. 묶음 갯수는 N개
// 그럼 O(N)으로 K 값 구할 수 있음

// 2번 줄이기
// 1번과 같은 길이의 연속된 구간합이 K인 부분배열의 갯수를 구해야함.
// 이것도 위와 같은 형태의 배열을 순회하기?.. 간단히 하는 방법이 뭘까...
// 노가다 ㄱㄱ
// 예) [2, 1, 5] -> [2, 2, 1, 5, 5, 5, 5, 5]
// 만약에 길이 2에 합이 3이다.
// 구간합 구해보기 -> [4, 3, 5, 10, 10, 10, 10] -> 이 배열로 구하면 N^2개를 봐야해서 안됨
// 구간합 분포는 K의 값에서 작다가 크다가 물결침. 그렇다면 크다가 작아지는 구간과 작다가 커지는 구간에 K값이 있을 수 있음. 이런 구간을 찾아서 K값이 있는지 체크하기. 여기서 구간합 값을 구하는데 드는 시간복잡도는 최대 2*N임. 왜냐면 구간합을 구하기 위해 슬라이딩 윈도우를 사용하는데, L R 중에 가장 빨리 묶음이 바뀌는 구간마다 값을 구할건데 이때 제일 많이 걸리는 시간은 L과 R이 각각 계속해서 바로 다음 묶음으로 바뀌는 경우임.
// 묶음 배열 -> [[1, 4], [2, 5], [7, 30]]
// 투포인터로 L과 R중 묶음이 바뀌는 가장 짧은 길이마다 구간값을 가져옴, 여기서 K 구할때랑 똑같이 진행하면 O(N^2)이라 안됨.

function solution(arr, l, r) {
  const packages = [];
  l--;
  r--;
  let [idx, sum] = [0, 0];
  arr.forEach((v, i) => {
    idx += v;
    sum += v * v;
    packages.push([idx - 1, sum, v]); // [현재 묶음에 해당하는 원소의 마지막 위치, 지금까지 합, 현재 묶음의 값]
  });

  // l,r 주어지면 구간합 반환
  const getRangeSum = (l, r) => {
    let ret = 0;
    let [lpass, rpass] = [false, false];
    for (let i = 0; i < packages.length; i++) {
      let [idx, sum, value] = packages[i];
      // l, r이 다른 묶음
      // l 앞에까지 합 빼기
      if (!lpass && idx >= l) {
        lpass = true;
        if (i - 1 >= 0) {
          let [_, sub, __] = packages[i - 1];
          ret -= sub;
        }
        if (l - 1 > idx - value) {
          ret -= (l - 1 - (idx - value)) * value;
        }
      }
      // r 까지의 합
      if (lpass && idx >= r) {
        rpass = true;
        ret += sum - (idx - r) * value;
        break;
      }
    }
    return ret;
  };

  let K = getRangeSum(l, r);

  // C 구하기
  // L과 R의 차가 동일하도록 진행. BRR에서 L과 R에 해당하는 값이 현재 값과 달라지는 지점까지 진행.
  // 그리고 +1하고 다시 BRR에서 L과 R에 해당하는 갑싱 현재 값과 달라지는 지점까지 진행
  // 각 값을 포함한 사이 구간에 K가 있는지 판단.
  let C = 0;
  [l, r] = [0, r - l];
  let [li, ri] = [0, 0]; // l, r에 해당하는 값의 arr에서의 위치
  for (let i = 0; i < arr.length; i++) {
    let [idx, _, __] = packages[i];
    if (idx > r) {
      ri = i;
      break;
    }
  }
  let rangeSum = getRangeSum(l, r);

  let brrEnd = arr.reduce((acc, cur) => acc + cur, 0);

  while (r < brrEnd) {
    // 등차수열인 구간 정하고 K 값이 가능한지 확인 후, 다음으로 이동
    let [lv, rv] = [arr[li], arr[ri]];
    let d = rv - lv;
    // 다음 구간까지 거리 구하기
    let [nl, nr] = [packages[li][0], packages[ri][0]];
    let range = Math.min(nl - l, nr - r);

    let endRangeSum = rangeSum + d * range;
    if (d === 0) {
      if (rangeSum === K) C += range + 1;
    } else {
      if (
        (rangeSum <= K && endRangeSum >= K) ||
        (rangeSum >= K && endRangeSum <= K)
      ) {
        if (Math.abs(endRangeSum - K) % d === 0) C++;
      }
    }

    l += range + 1;
    r += range + 1;
    if (r === brrEnd) break;
    // packages에서의 좌표 갱신. 현재 묶음의 마지막 인덱스보다 크다면 다음 묶음에 해당하는 인덱스로 업데이트
    rangeSum = endRangeSum - packages[li][2];
    if (l > packages[li][0]) li++;
    if (r > packages[ri][0]) ri++;
    rangeSum += packages[ri][2];
  }

  return [K, C];
}
